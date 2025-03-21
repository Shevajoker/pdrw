package com.pdrw.pdrw.bestmebelru.charts.piechart;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PieChart {

    private String name;
    private Integer value;
    private Extra extra;
}
