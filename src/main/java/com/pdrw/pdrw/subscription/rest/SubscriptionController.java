package com.pdrw.pdrw.subscription.rest;

import com.pdrw.pdrw.subscription.dto.SubscriptionDataDto;
import com.pdrw.pdrw.subscription.dto.SubscriptionDto;
import com.pdrw.pdrw.subscription.dto.SubscriptionDataForNotifyingDto;
import com.pdrw.pdrw.subscription.dto.SubscriptionResponse;
import com.pdrw.pdrw.subscription.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/subscription")
@RequiredArgsConstructor
@Tag(name = "Subscription")
@Validated
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Operation(summary = "Subscription create")
    @PostMapping
    public ResponseEntity<SubscriptionResponse> create(@RequestBody @Validated SubscriptionDto subscriptionDto) {
        return new ResponseEntity<>(subscriptionService.subscribe(subscriptionDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Subscription activate")
    @GetMapping("/active/{id}")
    public ResponseEntity<SubscriptionResponse> activeSubscription(@PathVariable UUID id) {
        return new ResponseEntity<>(subscriptionService.activeSubscription(id), HttpStatus.OK);
    }

    @Operation(summary = "Get Subscription data")
    @GetMapping("/data")
    public ResponseEntity<SubscriptionDataDto> getSubscriptionData() {
        return new ResponseEntity<>(subscriptionService.getSubscriptionData(), HttpStatus.OK);
    }

    @Operation(summary = "Subscription generate and send delete link")
    @GetMapping("/link/delete/{email}")
    public ResponseEntity<SubscriptionResponse> generateAndSendDeleteLink(@PathVariable String email) {
        return new ResponseEntity<>(subscriptionService.generateAndSendDeleteLink(email), HttpStatus.OK);
    }

    @Operation(summary = "Subscription delete")
    @GetMapping("/delete/{id}")
    public ResponseEntity<SubscriptionResponse> generateAndSendDeleteLink(@PathVariable UUID id) {
        return new ResponseEntity<>(subscriptionService.delete(id), HttpStatus.OK);
    }

    @Operation(summary = "Subscription request")
    @PostMapping("/subscribe")
    public ResponseEntity<Void> subscribe(@RequestBody @Validated SubscriptionDataForNotifyingDto subscriptionDataForNotifyingDto) {
        HttpStatusCode statusCode = subscriptionService.notifyToChatBot(subscriptionDataForNotifyingDto);
        return new ResponseEntity<>(statusCode);
    }
}