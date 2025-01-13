package com.pdrw.pdrw.triya.service.impl;

import com.pdrw.pdrw.triya.charts.piechart.Extra;
import com.pdrw.pdrw.triya.charts.piechart.PieChart;
import com.pdrw.pdrw.triya.charts.piechart.PieChartResponse;
import com.pdrw.pdrw.triya.service.PieChartTriyaRuService;
import com.pdrw.pdrw.triya.service.TriyaRuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PieChartTriyaRuServiceImpl implements PieChartTriyaRuService {

    private final TriyaRuService triyaRuService;

    @Override
    public PieChartResponse getPieChart() {
        PieChartResponse response = new PieChartResponse();
        List<String> typesList = triyaRuService.findAllTypes();
        for (String type : typesList) {
            Integer count = triyaRuService.countItemsByType(type);
            if (count > 0) {
                PieChart pieChart = new PieChart();
                pieChart.setName(type);
                pieChart.setValue(count);
                pieChart.setExtra(new Extra(type));
                response.getPieChartList().add(pieChart);
            }
        }

        return response;
    }
}
