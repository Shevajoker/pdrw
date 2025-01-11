package com.pdrw.pdrw.pinskdrevby.service.impl;

import com.pdrw.pdrw.pinskdrevby.charts.piechart.Extra;
import com.pdrw.pdrw.pinskdrevby.charts.piechart.PieChart;
import com.pdrw.pdrw.pinskdrevby.charts.piechart.PieChartResponse;
import com.pdrw.pdrw.pinskdrevby.service.PieChartPinskdrevByService;
import com.pdrw.pdrw.pinskdrevby.service.PinskdrevByService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PieChartPinskdrevByServiceImpl implements PieChartPinskdrevByService {

    private final PinskdrevByService pinskdrevByService;

    @Override
    public PieChartResponse getPieChart() {
        PieChartResponse response = new PieChartResponse();
        List<String> typesList = pinskdrevByService.findAllTypes();
        for (String type : typesList) {
            Integer count = pinskdrevByService.countItemsByType(type);
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
