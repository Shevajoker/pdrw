package com.pdrw.pdrw.subscription.dto;

import com.pdrw.pdrw.subscription.entity.Format;
import com.pdrw.pdrw.subscription.entity.Frequency;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SubscriptionDto {

    @Email
    String email;
    Format format;
    Frequency frequency;
}