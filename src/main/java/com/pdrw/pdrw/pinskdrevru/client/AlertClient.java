package com.pdrw.pdrw.pinskdrevru.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlertClient {

    @Value("${application.config.alert-url}")
    private String url;
    private final RestTemplate restTemplate;

    public void sendAlert(String message) {

        AlertRequest alertRequest = new AlertRequest();
        alertRequest.setKey("kWQYKdwtI2450aX7xGmj");
        alertRequest.setMessage(message);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<AlertRequest> entity = new HttpEntity<>(alertRequest, headers);
        ParameterizedTypeReference<String> typeReference = new ParameterizedTypeReference<String>() {
        };

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);
        responseEntity.getBody();
        HttpStatusCode statusCode = responseEntity.getStatusCode();

        log.atDebug().log("Response status code: {}", statusCode);
    }


}