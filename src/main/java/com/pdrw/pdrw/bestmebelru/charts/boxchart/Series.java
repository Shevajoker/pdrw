package com.pdrw.pdrw.bestmebelru.charts.boxchart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Series {

    private String name;
    private BigDecimal value;
}
