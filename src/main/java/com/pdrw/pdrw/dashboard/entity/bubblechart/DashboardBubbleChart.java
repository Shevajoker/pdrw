package com.pdrw.pdrw.dashboard.entity.bubblechart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardBubbleChart {

    String label;
    List<Dataset> data = new ArrayList<Dataset>();
}
