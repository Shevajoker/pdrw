package com.pdrw.pdrw.pinskdrevru.charts.verticalbarchart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerticalBarChartDto {

    private String type;
    List<VerticalBarChart> verticalBarCharts = new ArrayList<>();
}
