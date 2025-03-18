package com.pdrw.pdrw.dashboard.service.impl;

import com.pdrw.pdrw.bestmebelru.repository.BestmebelRuRepository;
import com.pdrw.pdrw.dashboard.entity.piechart.DashboardPieChart;
import com.pdrw.pdrw.dashboard.entity.piechart.Extra;
import com.pdrw.pdrw.dashboard.entity.piechart.dto.DashboardPieChartResponse;
import com.pdrw.pdrw.dashboard.service.DashboardPieChartService;
import com.pdrw.pdrw.pinskdrevru.repository.PinskdrevRuRepository;
import com.pdrw.pdrw.triya.repository.TriyaRuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

;

@Service
@RequiredArgsConstructor
public class DashboardPieChartServiceImpl implements DashboardPieChartService {

    private final PinskdrevRuRepository pinskdrevRuRepository;
    private final TriyaRuRepository triyaRuRepository;
    private final BestmebelRuRepository bestmebelRuRepository;

    @Override
    public DashboardPieChartResponse getPieChart() {
        DashboardPieChartResponse response = new DashboardPieChartResponse();

        DashboardPieChart pieChartPinskdrevRu = new DashboardPieChart();
        pieChartPinskdrevRu.setName("pinskdrev.ru");
        pieChartPinskdrevRu.setValue(pinskdrevRuRepository.countPinskdrevRuByActualTrue());
        pieChartPinskdrevRu.setExtra(new Extra("pinskdrev.ru"));

        DashboardPieChart pieChartTriyaRu = new DashboardPieChart();
        pieChartTriyaRu.setName("triya.ru");
        pieChartTriyaRu.setValue(triyaRuRepository.countByActualTrue());
        pieChartTriyaRu.setExtra(new Extra("triya.ru"));

        DashboardPieChart pieChartBestmebelRu = new DashboardPieChart();
        pieChartTriyaRu.setName("bestmebel.ru");
        pieChartTriyaRu.setValue(bestmebelRuRepository.countBestmebelRuByActualTrue());
        pieChartTriyaRu.setExtra(new Extra("bestmebel.ru"));

        response.getDashboardPieChartList().add(pieChartPinskdrevRu);
        response.getDashboardPieChartList().add(pieChartTriyaRu);
        response.getDashboardPieChartList().add(pieChartBestmebelRu);

        return response;
    }
}
