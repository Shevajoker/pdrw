package com.pdrw.pdrw.dashboard.entity.linechart.dto;

import com.pdrw.pdrw.dashboard.entity.linechart.DashboardLineChart;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardLineChartResponse {

    DashboardLineChart dashboardLineChart;
}
