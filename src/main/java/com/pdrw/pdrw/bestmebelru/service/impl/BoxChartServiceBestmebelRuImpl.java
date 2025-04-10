package com.pdrw.pdrw.bestmebelru.service.impl;


import com.pdrw.pdrw.bestmebelru.charts.boxchart.BoxChart;
import com.pdrw.pdrw.bestmebelru.charts.boxchart.BoxChartResponse;
import com.pdrw.pdrw.bestmebelru.charts.boxchart.Series;
import com.pdrw.pdrw.bestmebelru.model.BestmebelRu;
import com.pdrw.pdrw.bestmebelru.service.BestmebelRuService;
import com.pdrw.pdrw.bestmebelru.service.BoxChartBestmebelRuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoxChartServiceBestmebelRuImpl implements BoxChartBestmebelRuService {

    private final BestmebelRuService bestmebelRuService;

    @Override
    public BoxChartResponse getBoxChart() {
        BoxChartResponse boxChartResponse = new BoxChartResponse();
        List<String> types = bestmebelRuService.findAllTypes();
        for (var type : types) {
            BoxChart boxChart = new BoxChart();
            boxChart.setName(type);
            List<BestmebelRu> items = bestmebelRuService.findActualByType(type);
            BigDecimal q1;
            BigDecimal q2;
            BigDecimal q3;
            if (items != null && items.size() > 1) {
                if (items.size() % 2 == 0) {
                    q2 = items.get(items.size() / 2 - 1).getPriceNew().add(items.get(items.size() / 2).getPriceNew()).divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);
                } else {
                    q2 = items.get(items.size() / 2).getPriceNew();
                }
                List<BestmebelRu> q1List = items.subList(0, items.size() / 2);
                if (q1List.size() % 2 == 0) {
                    q1 = q1List.get(q1List.size() / 2 - 1).getPriceNew().add(q1List.get(q1List.size() / 2).getPriceNew()).divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);
                } else {
                    q1 = q1List.get(q1List.size() / 2).getPriceNew();
                }
                List<BestmebelRu> q3List = items.subList(items.size() / 2, items.size());
                if (q3List.size() % 2 == 0) {
                    q3 = q3List.get(q3List.size() / 2 - 1).getPriceNew().add(q3List.get(q3List.size() / 2).getPriceNew()).divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);
                } else {
                    q3 = q3List.get(q3List.size() / 2).getPriceNew();
                }
                Series seriesQ1 = Series.builder()
                        .name("Q1")
                        .value(q1)
                        .build();
                Series seriesQ2 = Series.builder()
                        .name("Q2")
                        .value(q2)
                        .build();
                Series seriesQ3 = Series.builder()
                        .name("Q3")
                        .value(q3)
                        .build();
                Series min = Series.builder()
                        .name("min")
                        .value(items.getFirst().getPriceNew())
                        .build();
                Series max = Series.builder()
                        .name("max")
                        .value(items.getLast().getPriceNew())
                        .build();
                boxChart.getSeries().add(seriesQ1);
                boxChart.getSeries().add(seriesQ2);
                boxChart.getSeries().add(seriesQ3);
                boxChart.getSeries().add(min);
                boxChart.getSeries().add(max);
            }
            boxChartResponse.getCharts().add(boxChart);
        }
        return boxChartResponse;
    }
}
