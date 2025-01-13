package com.pdrw.pdrw.triya.charts.verticalbarchart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerticalBarChart {

    private List<Label> labels = new ArrayList<>();
    private List<Dataset> datasets = new ArrayList<>();
}
