package com.pdrw.pdrw.pinskdrevby.service.impl;

import com.pdrw.pdrw.pinskdrevby.charts.verticalbarchart.*;
import com.pdrw.pdrw.pinskdrevby.model.PinskdrevBy;
import com.pdrw.pdrw.pinskdrevby.service.PinskdrevByService;
import com.pdrw.pdrw.pinskdrevby.service.VerticalBarChartPinskdrevByService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VerticalBarChartPinskdrevByServiceImpl implements VerticalBarChartPinskdrevByService {

    private final PinskdrevByService pinskdrevByService;

    @Override
    public VerticalBarChartResponse getVerticalBarChart() {

        VerticalBarChartResponse response = new VerticalBarChartResponse();
        List<String> types = pinskdrevByService.findAllTypes();

        for (String type : types) {
            List<PinskdrevBy> pinskdrevByList = pinskdrevByService.findActualWithSaleByType(type);
            if (!pinskdrevByList.isEmpty()) {
                VerticalBarChartDto verticalBarChartDto = new VerticalBarChartDto();
                verticalBarChartDto.setType(type);
                VerticalBarChart verticalBarChart = new VerticalBarChart();
                Dataset datasetNewPrice = new Dataset();
                datasetNewPrice.setLabel("new_price");
                Dataset datasetOldPrice = new Dataset();
                datasetOldPrice.setLabel("old_price");
                List<Label> labels = new ArrayList<>();
                for (PinskdrevBy pinskdrevBy : pinskdrevByList) {
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
