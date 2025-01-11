package com.pdrw.pdrw.pinskdrevby.charts.verticalbarchart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerticalBarChartResponse {

    private List<VerticalBarChartDto> data = new ArrayList<>();
}
