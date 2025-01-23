package com.pdrw.pdrw.dashboard.service.impl;

import com.pdrw.pdrw.dashboard.entity.treemap.DashboardTreeMap;
import com.pdrw.pdrw.dashboard.entity.treemap.Extra;
import com.pdrw.pdrw.dashboard.entity.treemap.dto.DashboardTreeMapResponse;
import com.pdrw.pdrw.dashboard.service.DashboardTreeMapService;
import com.pdrw.pdrw.pinskdrevru.repository.PinskdrevRuRepository;
import com.pdrw.pdrw.triya.repository.TriyaRuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardTreeMapServiceImpl implements DashboardTreeMapService {

    private final PinskdrevRuRepository pinskdrevRuRepository;
    private final TriyaRuRepository triyaRuRepository;

    @Override
    public DashboardTreeMapResponse getDashboardTreeMapResponse() {
        DashboardTreeMapResponse response = new DashboardTreeMapResponse();

        DashboardTreeMap pinskdrevRu = new DashboardTreeMap();
        pinskdrevRu.setName("pinskdrev.ru");
        pinskdrevRu.setExtra(new Extra().setLabel("pinskdrev.ru"));
        pinskdrevRu.setSize(pinskdrevRuRepository.summPriceNewActualTrue());
        response.getDashboardTreeMaps().add(pinskdrevRu);

        DashboardTreeMap triyaRu = new DashboardTreeMap();
        triyaRu.setName("triya.ru");
        triyaRu.setExtra(new Extra().setLabel("triya.ru"));
        triyaRu.setSize(triyaRuRepository.summPriceNewActualTrue());
        response.getDashboardTreeMaps().add(triyaRu);

        return response;
    }
}
