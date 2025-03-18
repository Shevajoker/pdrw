package com.pdrw.pdrw.bestmebelru.charts.groupedverticalbarchart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupedVerticalBarChartDto {

    private String type;
    List<GroupedVerticalBarChart> groupedVerticalBarCharts = new ArrayList<>();
}
