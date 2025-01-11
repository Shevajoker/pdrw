package com.pdrw.pdrw.pinskdrevby.charts.boxchart;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BoxChartResponse {

    private List<BoxChart> charts = new ArrayList<BoxChart>();
}
