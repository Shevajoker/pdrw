package com.pdrw.pdrw.dashboard.entity.bubblechart.dto;

import com.pdrw.pdrw.dashboard.entity.bubblechart.DashboardBubbleChart;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DashboardBubbleChartResponse {

    List<DashboardBubbleChart> datasets;
}
