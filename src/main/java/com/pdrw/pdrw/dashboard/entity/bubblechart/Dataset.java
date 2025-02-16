package com.pdrw.pdrw.dashboard.entity.bubblechart;

import lombok.Builder;

import java.math.BigDecimal;

@lombok.Data
@Builder
public class Dataset {

    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal r;

}
