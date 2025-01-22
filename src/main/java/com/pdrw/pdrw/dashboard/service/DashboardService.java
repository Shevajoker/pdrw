package com.pdrw.pdrw.dashboard.service;

import com.pdrw.pdrw.dashboard.entity.Dashboard;
import com.pdrw.pdrw.pinskdrevru.model.PinskdrevRu;
import com.pdrw.pdrw.triya.model.TriyaRu;

import java.util.List;

public interface DashboardService {

    List<Dashboard> findAll();

    void createDashboardPinskdrevRu(List<PinskdrevRu> pinskdrevRuList);

    void createDashboardTriya(List<TriyaRu> triyaRuList);
}
