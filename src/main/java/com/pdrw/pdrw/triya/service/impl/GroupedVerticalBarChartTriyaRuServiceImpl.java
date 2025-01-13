package com.pdrw.pdrw.triya.service.impl;

import com.pdrw.pdrw.triya.charts.groupedverticalbarchart.*;
import com.pdrw.pdrw.triya.model.TriyaRu;
import com.pdrw.pdrw.triya.service.GroupedVerticalBarChartTriyaRuService;
import com.pdrw.pdrw.triya.service.TriyaRuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupedVerticalBarChartTriyaRuServiceImpl implements GroupedVerticalBarChartTriyaRuService {

    private final TriyaRuService triyaRuService;

    @Override
    public GroupedVerticalBarChartResponse getGroupedVerticalBarChart() {
        GroupedVerticalBarChartResponse response = new GroupedVerticalBarChartResponse();
        List<String> types = triyaRuService.findAllTypes();

        for (String type : types) {
            List<TriyaRu> pinskdrevByList = triyaRuService.findActualWithSaleByType(type);
            if (!pinskdrevByList.isEmpty()) {
                GroupedVerticalBarChartDto groupedVerticalBarChartDto = new GroupedVerticalBarChartDto();
                groupedVerticalBarChartDto.setType(type);
                for (TriyaRu pinskdrevBy : pinskdrevByList) {
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
