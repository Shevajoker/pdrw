package com.pdrw.pdrw.pinskdrevru.charts.groupedverticalbarchart;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GroupedVerticalBarChartResponse {

    List<GroupedVerticalBarChartDto> groupedVerticalBarChartsDto = new ArrayList<GroupedVerticalBarChartDto>();
}
