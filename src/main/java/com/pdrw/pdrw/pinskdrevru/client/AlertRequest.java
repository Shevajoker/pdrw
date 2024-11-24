package com.pdrw.pdrw.pinskdrevru.client;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class AlertRequest {

    @Value("${application.config.alert-key}")
    private String key;
    private String message;
}
