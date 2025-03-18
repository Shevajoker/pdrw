package com.pdrw.pdrw.bestmebelru.service.impl;

import com.pdrw.pdrw.bestmebelru.model.BestmebelRu;
import com.pdrw.pdrw.bestmebelru.service.BestmebelRuService;
import com.pdrw.pdrw.bestmebelru.charts.groupedverticalbarchart.*;
import com.pdrw.pdrw.bestmebelru.service.GroupedVerticalBarChartBestmebelRuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupedVerticalBarChartBestmebelRuServiceImpl implements GroupedVerticalBarChartBestmebelRuService {

    private final BestmebelRuService bestmebelRuService;

    @Override
    public GroupedVerticalBarChartResponse getGroupedVerticalBarChart() {
        GroupedVerticalBarChartResponse response = new GroupedVerticalBarChartResponse();
        List<String> types = bestmebelRuService.findAllTypes();

        for (String type : types) {
            List<BestmebelRu> bestmebelRuList = bestmebelRuService.findActualWithSaleByType(type);
            if (!bestmebelRuList.isEmpty()) {
                GroupedVerticalBarChartDto groupedVerticalBarChartDto = new GroupedVerticalBarChartDto();
                groupedVerticalBarChartDto.setType(type);
                for (BestmebelRu bestmebelRu : bestmebelRuList) {
                    GroupedVerticalBarChart groupedVerticalBarChart = new GroupedVerticalBarChart();
                    groupedVerticalBarChart.setName(bestmebelRu.getName());
                    Series seriesNewPrice = new Series();
                    seriesNewPrice.setName("new_price");
                    seriesNewPrice.setExtra(new Extra(bestmebelRu.getArticle()));
                    seriesNewPrice.setValue(bestmebelRu.getPriceNew());
                    groupedVerticalBarChart.getSeries().add(seriesNewPrice);
                    Series seriesOldPrice = new Series();
                    seriesOldPrice.setName("old_price");
                    seriesOldPrice.setExtra(new Extra(bestmebelRu.getArticle()));
                    seriesOldPrice.setValue(bestmebelRu.getPriceOld());
                    groupedVerticalBarChart.getSeries().add(seriesOldPrice);

                    groupedVerticalBarChartDto.getGroupedVerticalBarCharts().add(groupedVerticalBarChart);
                }
                response.getGroupedVerticalBarChartsDto().add(groupedVerticalBarChartDto);
            }
        }

        return response;
    }
}
