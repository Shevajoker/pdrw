package com.pdrw.pdrw.bestmebelru.service.impl;

import com.pdrw.pdrw.bestmebelru.service.BestmebelRuService;
import com.pdrw.pdrw.bestmebelru.charts.piechart.Extra;
import com.pdrw.pdrw.bestmebelru.charts.piechart.PieChart;
import com.pdrw.pdrw.bestmebelru.charts.piechart.PieChartResponse;
import com.pdrw.pdrw.bestmebelru.service.PieChartBestmebelRuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PieChartBestmebelRuServiceImpl implements PieChartBestmebelRuService {

    private final BestmebelRuService bestmebelRuService;

    @Override
    public PieChartResponse getPieChart() {
        PieChartResponse response = new PieChartResponse();
        List<String> typesList = bestmebelRuService.findAllTypes();
        for (String type : typesList) {
            Integer count = bestmebelRuService.countItemsByType(type);
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
