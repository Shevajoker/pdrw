package com.pdrw.pdrw.triya.service.impl;

import com.pdrw.pdrw.triya.charts.verticalbarchart.*;
import com.pdrw.pdrw.triya.model.TriyaRu;
import com.pdrw.pdrw.triya.service.TriyaRuService;
import com.pdrw.pdrw.triya.service.VerticalBarChartTriyaRuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VerticalBarChartTriyaRuServiceImpl implements VerticalBarChartTriyaRuService {

    private final TriyaRuService triyaRuService;

    @Override
    public VerticalBarChartResponse getVerticalBarChart() {

        VerticalBarChartResponse response = new VerticalBarChartResponse();
        List<String> types = triyaRuService.findAllTypes();

        for (String type : types) {
            List<TriyaRu> triyaRuList = triyaRuService.findActualWithSaleByType(type);
            if (!triyaRuList.isEmpty()) {
                VerticalBarChartDto verticalBarChartDto = new VerticalBarChartDto();
                verticalBarChartDto.setType(type);
                VerticalBarChart verticalBarChart = new VerticalBarChart();
                Dataset datasetNewPrice = new Dataset();
                datasetNewPrice.setLabel("new_price");
                Dataset datasetOldPrice = new Dataset();
                datasetOldPrice.setLabel("old_price");
                List<Label> labels = new ArrayList<>();
                for (TriyaRu pinskdrevBy : triyaRuList) {
                    Label label = new Label();
                    label.setName(pinskdrevBy.getName());
                    label.setLink(pinskdrevBy.getLink());
                    datasetNewPrice.getData().add(pinskdrevBy.getPriceNew());
                    datasetOldPrice.getData().add(pinskdrevBy.getPriceOld());
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
