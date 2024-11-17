package com.pdrw.pdrw.pinskdrevru.charts.piechart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PieChartResponse {

    private List<PieChart> pieChartList = new ArrayList<>();
}
