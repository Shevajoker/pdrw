package com.pdrw.pdrw.pinskdrevru.charts.boxchart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoxChart {

    private String name;
    private List<Series> series = new ArrayList<Series>();
}
