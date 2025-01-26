package com.pdrw.pdrw.dashboard.entity.linechart;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DashboardLineChart {

    List<String> labels = new ArrayList<>();
    List<Dataset> datasets = new ArrayList<>();


}
