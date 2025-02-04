package com.pdrw.pdrw.dashboard.entity.piechart.dto;

import com.pdrw.pdrw.dashboard.entity.piechart.DashboardPieChart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardPieChartResponse {

    private List<DashboardPieChart> dashboardPieChartList = new ArrayList<>();
}
