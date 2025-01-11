package com.pdrw.pdrw.pinskdrevby.charts.groupedverticalbarchart;

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
    private Extra extra;
}
