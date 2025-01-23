package com.pdrw.pdrw.dashboard.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class MaxDiscountProduct {

    private BigDecimal maxDiscountPercentage;
    private String maxProductUrl;
}
