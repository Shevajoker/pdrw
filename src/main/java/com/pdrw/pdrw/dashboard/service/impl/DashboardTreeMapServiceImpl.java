package com.pdrw.pdrw.dashboard.service.impl;

import com.pdrw.pdrw.dashboard.entity.treemap.DashboardTreeMap;
import com.pdrw.pdrw.dashboard.entity.treemap.Extra;
import com.pdrw.pdrw.dashboard.entity.treemap.dto.DashboardTreeMapResponse;
import com.pdrw.pdrw.dashboard.service.DashboardTreeMapService;
import com.pdrw.pdrw.pinskdrevby.repository.PinskdrevByRepository;
import com.pdrw.pdrw.pinskdrevru.repository.PinskdrevRuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardTreeMapServiceImpl implements DashboardTreeMapService {

    private final PinskdrevRuRepository pinskdrevRuRepository;
    private final PinskdrevByRepository pinskdrevByRepository;

    @Override
    public DashboardTreeMapResponse getDashboardTreeMapResponse() {
        DashboardTreeMapResponse response = new DashboardTreeMapResponse();

        DashboardTreeMap pinskdrevRu = new DashboardTreeMap();
        pinskdrevRu.setName("pinskdrev.ru");
        pinskdrevRu.setExtra(new Extra().setLabel("pinskdrev.ru"));
        pinskdrevRu.setSize(pinskdrevRuRepository.summPriceNewActualTrue());
        response.getDashboardTreeMaps().add(pinskdrevRu);

        DashboardTreeMap pinskdrevBy = new DashboardTreeMap();
        pinskdrevBy.setName("pinskdrev.by");
        pinskdrevBy.setExtra(new Extra().setLabel("pinskdrev.by"));
        pinskdrevBy.setSize(pinskdrevByRepository.summPriceNewActualTrue());
        response.getDashboardTreeMaps().add(pinskdrevBy);

        return response;
    }
}
