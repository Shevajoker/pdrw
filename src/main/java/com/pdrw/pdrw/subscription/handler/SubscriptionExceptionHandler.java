package com.pdrw.pdrw.subscription.handler;

import com.pdrw.pdrw.subscription.dto.SubscriptionResponse;
import com.pdrw.pdrw.subscription.exception.SubscriptionException;
import com.pdrw.pdrw.subscription.exception.SubscriptionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class SubscriptionExceptionHandler {

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<SubscriptionResponse> handleFormatOrFrequency() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(SubscriptionResponse.builder().error("Invalid input data.").build());
    }

    @ExceptionHandler(SubscriptionException.class)
    public ResponseEntity<SubscriptionResponse> handleLimitSubscription() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(SubscriptionResponse.builder().error("Subscription limit exceeded. Maximum allowed: 5.").build());
    }

    @ExceptionHandler(SubscriptionNotFoundException.class)
    public ResponseEntity<SubscriptionResponse> handleNotFoundSubscription(SubscriptionNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(SubscriptionResponse.builder().error(e.getMessage()).build());
    }
}