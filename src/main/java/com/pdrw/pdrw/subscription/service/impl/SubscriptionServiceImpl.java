package com.pdrw.pdrw.subscription.service.impl;

import com.pdrw.pdrw.pinskdrevru.client.AlertRequest;
import com.pdrw.pdrw.security.entity.User;
import com.pdrw.pdrw.security.service.UserService;
import com.pdrw.pdrw.subscription.dto.SubscriptionDataDto;
import com.pdrw.pdrw.subscription.dto.SubscriptionDto;
import com.pdrw.pdrw.subscription.dto.SubscriptionDataForNotifyingDto;
import com.pdrw.pdrw.subscription.dto.SubscriptionResponse;
import com.pdrw.pdrw.subscription.entity.Subscription;
import com.pdrw.pdrw.subscription.exception.SubscriptionException;
import com.pdrw.pdrw.subscription.exception.SubscriptionNotFoundException;
import com.pdrw.pdrw.subscription.repository.SubscriptionRepository;
import com.pdrw.pdrw.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserService userService;
    private final JavaMailSender javaMailSender;
    private final AlertRequest alertRequest;
    private final RestTemplate restTemplate;

    @Value("${link.active.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${application.config.alert-url}")
    private String alertUrl;


    @Override
    @Transactional
    public SubscriptionResponse subscribe(SubscriptionDto subscriptionDto) {
        User currentUser = userService.getCurrentUser();

        Integer countSubscriptions = subscriptionRepository.countSubscriptionsByUserId(currentUser.getId()).orElse(0);

        if (countSubscriptions > 4) {
            throw new SubscriptionException();
        }

        Subscription build = Subscription.builder()
                .email(subscriptionDto.getEmail())
                .format(subscriptionDto.getFormat())
                .frequency(subscriptionDto.getFrequency())
                .user(currentUser)
                .build();

        Subscription subscription = subscriptionRepository.save(build);

        sendMessage(subscriptionDto.getEmail(), "Validation link subscription", host + "/api/v1/subscription/active/" + subscription.getId());

        return SubscriptionResponse.builder().message("Validation link sent to email. Please confirm within 12 hours.").build();
    }

    @Override
    @Transactional
    public SubscriptionResponse activeSubscription(UUID id) {
        Subscription subscription = subscriptionRepository.findById(id).orElse(null);

        if (subscription == null || subscription.getCreateDate().isBefore(LocalDateTime.now().minusHours(12))) {
            return SubscriptionResponse.builder().message("Link expiration date confirmed").build();
        }

        subscription.setActive(true);

        subscriptionRepository.save(subscription);

        return SubscriptionResponse.builder().message("Subscription activated").build();
    }

    @Override
    public SubscriptionDataDto getSubscriptionData() {
        User currentUser = userService.getCurrentUser();

        Integer countSubscriptions = subscriptionRepository.countSubscriptionsByUserId(currentUser.getId()).orElse(0);

        if (countSubscriptions == 0) {
            return SubscriptionDataDto.builder().count(countSubscriptions).emails(Collections.emptyList()).build();
        }

        List<String> emails = subscriptionRepository.findEmailsByUserId(currentUser.getId());

        return SubscriptionDataDto.builder().count(countSubscriptions).emails(emails).build();
    }

    @Override
    public SubscriptionResponse generateAndSendDeleteLink(String email) {
        Subscription subscription = subscriptionRepository.findByEmail(email)
                .orElseThrow(() -> new SubscriptionNotFoundException("Subscription not found with email: " + email));

        sendMessage(email, "Removal link", host + "/api/v1/subscription/delete/" + subscription.getId());

        return SubscriptionResponse.builder().message("Removal link sent by email").build();
    }

    @Override
    @Transactional
    public SubscriptionResponse delete(UUID id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new SubscriptionNotFoundException("Subscription not found with id: " + id));

        subscriptionRepository.delete(subscription);

        return SubscriptionResponse.builder().message("Subscription deleted").build();
    }

    @Transactional
    @Scheduled(cron = "0 0 2 * * ?")
    public void deleteSubscriptionInactive() {
        List<Subscription> subscriptions = subscriptionRepository.findByActiveAndCreateDateBefore(false, LocalDateTime.now().minusHours(12));

        subscriptionRepository.deleteAll(subscriptions);
    }

    private void sendMessage(String email, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom(username);

        javaMailSender.send(message);
    }

    @Override
    public HttpStatusCode notifyToChatBot(SubscriptionDataForNotifyingDto data) {
        alertRequest.setMessage(data.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<AlertRequest> entity = new HttpEntity<>(alertRequest, headers);
        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(alertUrl, entity, String.class);
            HttpStatusCode statusCode = responseEntity.getStatusCode();
            log.atInfo().log("Response status code for ChatBot service: {}", statusCode);
            return statusCode;
        } catch (RestClientException e) {
            HttpStatusCode statusCode = HttpStatus.BAD_GATEWAY;
            log.atInfo().log("Response status code for ChatBot service: {}", statusCode);
            return statusCode;
        }
    }
}