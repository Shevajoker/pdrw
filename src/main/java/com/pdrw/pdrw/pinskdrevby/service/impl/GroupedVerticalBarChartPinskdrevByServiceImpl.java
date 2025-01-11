package com.pdrw.pdrw.pinskdrevby.service.impl;

import com.pdrw.pdrw.pinskdrevby.model.PinskdrevBy;
import com.pdrw.pdrw.pinskdrevby.service.GroupedVerticalBarChartPinskdrevByService;
import com.pdrw.pdrw.pinskdrevby.service.PinskdrevByService;
import com.pdrw.pdrw.pinskdrevru.charts.groupedverticalbarchart.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupedVerticalBarChartPinskdrevByServiceImpl implements GroupedVerticalBarChartPinskdrevByService {

    private final PinskdrevByService pinskdrevByService;

    @Override
    public GroupedVerticalBarChartResponse getGroupedVerticalBarChart() {
        GroupedVerticalBarChartResponse response = new GroupedVerticalBarChartResponse();
        List<String> types = pinskdrevByService.findAllTypes();

        for (String type : types) {
            List<PinskdrevBy> pinskdrevByList = pinskdrevByService.findActualWithSaleByType(type);
            if (!pinskdrevByList.isEmpty()) {
                GroupedVerticalBarChartDto groupedVerticalBarChartDto = new GroupedVerticalBarChartDto();
                groupedVerticalBarChartDto.setType(type);
                for (PinskdrevBy pinskdrevBy : pinskdrevByList) {
                    GroupedVerticalBarChart groupedVerticalBarChart = new GroupedVerticalBarChart();
                    groupedVerticalBarChart.setName(pinskdrevBy.getName());
                    Series seriesNewPrice = new Series();
                    seriesNewPrice.setName("new_price");
                    seriesNewPrice.setExtra(new Extra(pinskdrevBy.getArticle()));
                    seriesNewPrice.setValue(pinskdrevBy.getPriceNew());
                    groupedVerticalBarChart.getSeries().add(seriesNewPrice);
                    Series seriesOldPrice = new Series();
                    seriesOldPrice.setName("old_price");
                    seriesOldPrice.setExtra(new Extra(pinskdrevBy.getArticle()));
                    seriesOldPrice.setValue(pinskdrevBy.getPriceOld());
                    groupedVerticalBarChart.getSeries().add(seriesOldPrice);

                    groupedVerticalBarChartDto.getGroupedVerticalBarCharts().add(groupedVerticalBarChart);
                }
                response.getGroupedVerticalBarChartsDto().add(groupedVerticalBarChartDto);
            }
        }

        return response;
    }
}
