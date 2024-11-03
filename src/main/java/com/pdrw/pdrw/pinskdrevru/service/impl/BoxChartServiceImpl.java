package com.pdrw.pdrw.pinskdrevru.service.impl;

import com.pdrw.pdrw.pinskdrevru.charts.boxchart.BoxChart;
import com.pdrw.pdrw.pinskdrevru.charts.boxchart.BoxChartResponse;
import com.pdrw.pdrw.pinskdrevru.charts.boxchart.Series;
import com.pdrw.pdrw.pinskdrevru.model.PinskdrevRu;
import com.pdrw.pdrw.pinskdrevru.service.BoxChartService;
import com.pdrw.pdrw.pinskdrevru.service.PinskdrevRuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoxChartServiceImpl implements BoxChartService {

    private final PinskdrevRuService pinskdrevRuService;

    @Override
    public BoxChartResponse getBoxChart() {
        BoxChartResponse boxChartResponse = new BoxChartResponse();
        List<String> types = pinskdrevRuService.findAllTypes();
        for (var type : types) {
            BoxChart boxChart = new BoxChart();
            boxChart.setName(type);
            List<PinskdrevRu> items = pinskdrevRuService.findActualByType(type);
            for (var item : items) {
                Series series = Series.builder()
                        .name(item.getName())
                        .value(item.getPriceNew())
                        .build();
                boxChart.getSeries().add(series);
            }
            boxChartResponse.getCharts().add(boxChart);
        }
        return boxChartResponse;
    }
}
