package com.pdrw.pdrw.dashboard.entity.treemap.dto;

import com.pdrw.pdrw.dashboard.entity.treemap.DashboardTreeMap;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DashboardTreeMapResponse {

    private List<DashboardTreeMap> dashboardTreeMaps = new ArrayList<DashboardTreeMap>();
}
