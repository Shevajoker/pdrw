package com.pdrw.pdrw.bestmebelru.service.impl;

import com.pdrw.pdrw.bestmebelru.model.BestmebelRu;
import com.pdrw.pdrw.bestmebelru.service.BestmebelRuService;
import com.pdrw.pdrw.bestmebelru.service.VerticalBarChartBestmebelRuService;
import com.pdrw.pdrw.bestmebelru.charts.verticalbarchart.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VerticalBarChartBestebelRuServiceImpl implements VerticalBarChartBestmebelRuService {

    private final BestmebelRuService bestmebelRuService;

    @Override
    public VerticalBarChartResponse getVerticalBarChart() {

        VerticalBarChartResponse response = new VerticalBarChartResponse();
        List<String> types = bestmebelRuService.findAllTypes();

        for (String type : types) {
            List<BestmebelRu> bestmebelRuList = bestmebelRuService.findActualWithSaleByType(type);
            if (!bestmebelRuList.isEmpty()) {
                VerticalBarChartDto verticalBarChartDto = new VerticalBarChartDto();
                verticalBarChartDto.setType(type);
                VerticalBarChart verticalBarChart = new VerticalBarChart();
                Dataset datasetNewPrice = new Dataset();
                datasetNewPrice.setLabel("new_price");
                Dataset datasetOldPrice = new Dataset();
                datasetOldPrice.setLabel("old_price");
                List<Label> labels = new ArrayList<>();
                for (BestmebelRu bestmebelRu : bestmebelRuList) {
                    Label label = new Label();
                    label.setName(bestmebelRu.getName());
                    label.setLink(bestmebelRu.getLink());
                    datasetNewPrice.getData().add(bestmebelRu.getPriceNew());
                    datasetOldPrice.getData().add(bestmebelRu.getPriceOld());
                    labels.add(label);
                }
                verticalBarChart.setLabels(labels);
                verticalBarChart.getDatasets().add(datasetNewPrice);
                verticalBarChart.getDatasets().add(datasetOldPrice);
                verticalBarChartDto.getVerticalBarCharts().add(verticalBarChart);
                response.getData().add(verticalBarChartDto);
            }
        }

        return response;
    }
}
