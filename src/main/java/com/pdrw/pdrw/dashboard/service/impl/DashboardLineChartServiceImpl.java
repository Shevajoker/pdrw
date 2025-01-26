package com.pdrw.pdrw.dashboard.service.impl;

import com.pdrw.pdrw.dashboard.entity.linechart.DashboardLineChart;
import com.pdrw.pdrw.dashboard.entity.linechart.Dataset;
import com.pdrw.pdrw.dashboard.entity.linechart.dto.DashboardLineChartResponse;
import com.pdrw.pdrw.dashboard.service.DashboardLineChartService;
import com.pdrw.pdrw.pinskdrevru.repository.PinskdrevRuRepository;
import com.pdrw.pdrw.triya.repository.TriyaRuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardLineChartServiceImpl implements DashboardLineChartService {

    private final PinskdrevRuRepository pinskdrevRuRepository;
    private final TriyaRuRepository triyaRuRepository;

    @Override
    public DashboardLineChartResponse getDashboardLineChartResponse() {

        DashboardLineChart lineChart = new DashboardLineChart();
        lineChart.getLabels().add("<20");
        lineChart.getLabels().add("20-40");
        lineChart.getLabels().add("40-60");
        lineChart.getLabels().add("60-80");
        lineChart.getLabels().add("80-100");
        lineChart.getLabels().add("100-120");
        lineChart.getLabels().add("120-140");
        lineChart.getLabels().add("140-160");
        lineChart.getLabels().add("160-180");
        lineChart.getLabels().add("180-200");
        lineChart.getLabels().add(">200");

        List<Integer> pinskdrevData = new ArrayList<>();
        pinskdrevData.add(pinskdrevRuRepository.findDataForDashboardLineChart(BigDecimal.ZERO, BigDecimal.valueOf(20_000)));
        pinskdrevData.add(pinskdrevRuRepository.findDataForDashboardLineChart(BigDecimal.valueOf(20_000), BigDecimal.valueOf(40_000)));
        pinskdrevData.add(pinskdrevRuRepository.findDataForDashboardLineChart(BigDecimal.valueOf(40_000), BigDecimal.valueOf(60_000)));
        pinskdrevData.add(pinskdrevRuRepository.findDataForDashboardLineChart(BigDecimal.valueOf(60_000), BigDecimal.valueOf(80_000)));
        pinskdrevData.add(pinskdrevRuRepository.findDataForDashboardLineChart(BigDecimal.valueOf(80_000), BigDecimal.valueOf(100_000)));
        pinskdrevData.add(pinskdrevRuRepository.findDataForDashboardLineChart(BigDecimal.valueOf(100_000), BigDecimal.valueOf(120_000)));
        pinskdrevData.add(pinskdrevRuRepository.findDataForDashboardLineChart(BigDecimal.valueOf(120_000), BigDecimal.valueOf(140_000)));
        pinskdrevData.add(pinskdrevRuRepository.findDataForDashboardLineChart(BigDecimal.valueOf(140_000), BigDecimal.valueOf(160_000)));
        pinskdrevData.add(pinskdrevRuRepository.findDataForDashboardLineChart(BigDecimal.valueOf(160_000), BigDecimal.valueOf(180_000)));
        pinskdrevData.add(pinskdrevRuRepository.findDataForDashboardLineChart(BigDecimal.valueOf(180_000), BigDecimal.valueOf(200_000)));
        pinskdrevData.add(pinskdrevRuRepository.findDataForDashboardLineChart(BigDecimal.valueOf(200_000), BigDecimal.valueOf(200_000_000)));
        lineChart.getDatasets().add(Dataset.builder()
                .label("pinskdrev.ru")
                .data(pinskdrevData)
                .build());
        List<Integer> triyaData = new ArrayList<>();
        triyaData.add(triyaRuRepository.findDataForDashboardLineChart(BigDecimal.ZERO, BigDecimal.valueOf(20_000)));
        triyaData.add(triyaRuRepository.findDataForDashboardLineChart(BigDecimal.valueOf(20_000), BigDecimal.valueOf(40_000)));
        triyaData.add(triyaRuRepository.findDataForDashboardLineChart(BigDecimal.valueOf(40_000), BigDecimal.valueOf(60_000)));
        triyaData.add(triyaRuRepository.findDataForDashboardLineChart(BigDecimal.valueOf(60_000), BigDecimal.valueOf(80_000)));
        triyaData.add(triyaRuRepository.findDataForDashboardLineChart(BigDecimal.valueOf(80_000), BigDecimal.valueOf(100_000)));
        triyaData.add(triyaRuRepository.findDataForDashboardLineChart(BigDecimal.valueOf(100_000), BigDecimal.valueOf(120_000)));
        triyaData.add(triyaRuRepository.findDataForDashboardLineChart(BigDecimal.valueOf(120_000), BigDecimal.valueOf(140_000)));
        triyaData.add(triyaRuRepository.findDataForDashboardLineChart(BigDecimal.valueOf(140_000), BigDecimal.valueOf(160_000)));
        triyaData.add(triyaRuRepository.findDataForDashboardLineChart(BigDecimal.valueOf(160_000), BigDecimal.valueOf(180_000)));
        triyaData.add(triyaRuRepository.findDataForDashboardLineChart(BigDecimal.valueOf(180_000), BigDecimal.valueOf(200_000)));
        triyaData.add(triyaRuRepository.findDataForDashboardLineChart(BigDecimal.valueOf(200_000), BigDecimal.valueOf(200_000_000)));
        lineChart.getDatasets().add(Dataset.builder()
                .label("triya.ru")
                .data(triyaData)
                .build());

        return DashboardLineChartResponse.builder()
                .dashboardLineChart(lineChart)
                .build();
    }
}
