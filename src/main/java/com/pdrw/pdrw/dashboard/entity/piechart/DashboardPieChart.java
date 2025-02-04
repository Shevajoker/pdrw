package com.pdrw.pdrw.dashboard.entity.piechart;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardPieChart {

    private String name;
    private Long value;
    private Extra extra;
}
