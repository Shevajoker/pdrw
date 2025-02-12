package com.pdrw.pdrw.subscription.service;

import com.pdrw.pdrw.subscription.dto.SubscriptionDataDto;
import com.pdrw.pdrw.subscription.dto.SubscriptionDto;
import com.pdrw.pdrw.subscription.dto.SubscriptionResponse;

import java.util.UUID;

public interface SubscriptionService {

    SubscriptionResponse subscribe(SubscriptionDto subscriptionDto);

    SubscriptionResponse activeSubscription(UUID id);

    SubscriptionDataDto getSubscriptionData();

    SubscriptionResponse generateAndSendDeleteLink(String email);

    SubscriptionResponse delete(UUID id);
}