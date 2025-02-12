package com.pdrw.pdrw.subscription.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubscriptionResponse {

    String message;
    String error;
}