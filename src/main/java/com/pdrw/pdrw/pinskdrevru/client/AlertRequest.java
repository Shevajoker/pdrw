package com.pdrw.pdrw.pinskdrevru.client;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class AlertRequest {

    @Value("${application.config.alert-key}")
    private String key;
    private String message;
}
