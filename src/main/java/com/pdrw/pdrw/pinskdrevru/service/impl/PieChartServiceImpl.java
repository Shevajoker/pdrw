package com.pdrw.pdrw.pinskdrevru.service.impl;

import com.pdrw.pdrw.pinskdrevru.charts.piechart.Extra;
import com.pdrw.pdrw.pinskdrevru.charts.piechart.PieChart;
import com.pdrw.pdrw.pinskdrevru.charts.piechart.PieChartResponse;
import com.pdrw.pdrw.pinskdrevru.service.PieChartService;
import com.pdrw.pdrw.pinskdrevru.service.PinskdrevRuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PieChartServiceImpl implements PieChartService {

    private final PinskdrevRuService pinskdrevRuService;

    @Override
    public PieChartResponse getPieChart() {
        PieChartResponse response = new PieChartResponse();
        List<String> typesList = pinskdrevRuService.findAllTypes();
        for (String type : typesList) {
            Integer count = pinskdrevRuService.countItemsByType(type);
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
