package com.pdrw.pdrw.pinskdrevru.service.impl;

import com.pdrw.pdrw.pinskdrevru.charts.verticalbarchart.*;
import com.pdrw.pdrw.pinskdrevru.model.PinskdrevRu;
import com.pdrw.pdrw.pinskdrevru.service.PinskdrevRuService;
import com.pdrw.pdrw.pinskdrevru.service.VerticalBarChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VerticalBarChartServiceImpl implements VerticalBarChartService {

    private final PinskdrevRuService pinskdrevRuService;

    @Override
    public VerticalBarChartResponse getVerticalBarChart() {

        VerticalBarChartResponse response = new VerticalBarChartResponse();
        List<String> types = pinskdrevRuService.findAllTypes();

        for (String type : types) {
            List<PinskdrevRu> pinskdrevRuList = pinskdrevRuService.findActualWithSaleByType(type);
            if (!pinskdrevRuList.isEmpty()) {
                VerticalBarChartDto verticalBarChartDto = new VerticalBarChartDto();
                verticalBarChartDto.setType(type);
                VerticalBarChart verticalBarChart = new VerticalBarChart();
                Dataset datasetNewPrice = new Dataset();
                datasetNewPrice.setLabel("new_price");
                Dataset datasetOldPrice = new Dataset();
                datasetOldPrice.setLabel("old_price");
                List<Label> labels = new ArrayList<>();
                for (PinskdrevRu pinskdrevRu : pinskdrevRuList) {
                    Label label = new Label();
                    label.setName(pinskdrevRu.getName());
                    label.setLink(pinskdrevRu.getLink());
                    datasetNewPrice.getData().add(pinskdrevRu.getPriceNew());
                    datasetOldPrice.getData().add(pinskdrevRu.getPriceOld());
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
