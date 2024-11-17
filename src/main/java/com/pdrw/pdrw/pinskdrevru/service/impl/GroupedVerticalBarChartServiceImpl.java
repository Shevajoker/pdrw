package com.pdrw.pdrw.pinskdrevru.service.impl;

import com.pdrw.pdrw.pinskdrevru.charts.groupedverticalbarchart.*;
import com.pdrw.pdrw.pinskdrevru.model.PinskdrevRu;
import com.pdrw.pdrw.pinskdrevru.service.GroupedVerticalBarChartService;
import com.pdrw.pdrw.pinskdrevru.service.PinskdrevRuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupedVerticalBarChartServiceImpl implements GroupedVerticalBarChartService {

    private final PinskdrevRuService pinskdrevRuService;

    @Override
    public GroupedVerticalBarChartResponse getGroupedVerticalBarChart() {
        GroupedVerticalBarChartResponse response = new GroupedVerticalBarChartResponse();
        List<String> types = pinskdrevRuService.findAllTypes();

        for (String type : types) {
            List<PinskdrevRu> pinskdrevRuList = pinskdrevRuService.findActualWithSaleByType(type);
            if (!pinskdrevRuList.isEmpty()) {
                GroupedVerticalBarChartDto groupedVerticalBarChartDto = new GroupedVerticalBarChartDto();
                groupedVerticalBarChartDto.setType(type);
                for (PinskdrevRu pinskdrevRu : pinskdrevRuList) {
                    GroupedVerticalBarChart groupedVerticalBarChart = new GroupedVerticalBarChart();
                    groupedVerticalBarChart.setName(pinskdrevRu.getName());
                    Series seriesNewPrice = new Series();
                    seriesNewPrice.setName("new_price");
                    seriesNewPrice.setExtra(new Extra(pinskdrevRu.getArticle()));
                    seriesNewPrice.setValue(pinskdrevRu.getPriceNew());
                    groupedVerticalBarChart.getSeries().add(seriesNewPrice);
                    Series seriesOldPrice = new Series();
                    seriesOldPrice.setName("old_price");
                    seriesOldPrice.setExtra(new Extra(pinskdrevRu.getArticle()));
                    seriesOldPrice.setValue(pinskdrevRu.getPriceOld());
                    groupedVerticalBarChart.getSeries().add(seriesOldPrice);

                    groupedVerticalBarChartDto.getGroupedVerticalBarCharts().add(groupedVerticalBarChart);
                }
                response.getGroupedVerticalBarChartsDto().add(groupedVerticalBarChartDto);
            }
        }

        return response;
    }
}
