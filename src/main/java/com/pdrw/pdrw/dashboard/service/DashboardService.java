package com.pdrw.pdrw.dashboard.service;

import com.pdrw.pdrw.dashboard.entity.Dashboard;

import java.util.List;

public interface DashboardService {

    List<Dashboard> findAll();

    void createDashboardPinskdrevRu();

    void createDashboardTriya();

    void createDashboardBestmebelRu();
}
