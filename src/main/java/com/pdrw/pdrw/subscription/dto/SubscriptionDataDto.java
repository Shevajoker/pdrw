package com.pdrw.pdrw.subscription.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class SubscriptionDataDto {

    Integer count;
    List<String> emails;
}