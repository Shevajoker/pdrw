package com.pdrw.pdrw.dashboard.service.impl;

import com.pdrw.pdrw.dashboard.entity.bubblechart.DashboardBubbleChart;
import com.pdrw.pdrw.dashboard.entity.bubblechart.Dataset;
import com.pdrw.pdrw.dashboard.entity.bubblechart.dto.DashboardBubbleChartResponse;
import com.pdrw.pdrw.dashboard.service.DashboardBubbleChartService;
import com.pdrw.pdrw.pinskdrevru.model.PinskdrevRu;
import com.pdrw.pdrw.pinskdrevru.repository.PinskdrevRuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardBubbleChartServiceImpl implements DashboardBubbleChartService {

    private final PinskdrevRuRepository pinskdrevRuRepository;

    @Override
    public DashboardBubbleChartResponse getDashboardBubbleChart() {

        List<PinskdrevRu> pinskdrevRuList = pinskdrevRuRepository.findAllActual();
        Map<BigDecimal, List<PinskdrevRu>> map = pinskdrevRuList.stream().collect(Collectors.groupingBy(PinskdrevRu::getDiscount));
        List<BigDecimal> discountList = map.keySet().stream().sorted().toList();
        DashboardBubbleChart dashboardBubbleChartPinskdrevRu = new DashboardBubbleChart();
        dashboardBubbleChartPinskdrevRu.setLabel("PinskdrevRu");
        for (BigDecimal discount : discountList) {
            BigDecimal x = BigDecimal.valueOf(map.get(discount).size());
            BigDecimal y = map.get(discount).stream().map(PinskdrevRu::getPriceNew)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(BigDecimal.valueOf(map.get(discount).size()), 2, RoundingMode.HALF_UP);

            Dataset dataset = Dataset.builder()
                    .x(x)
                    .y(y)
                    .r(discount)
                    .build();
            dashboardBubbleChartPinskdrevRu.getData().add(dataset);
        }


        return DashboardBubbleChartResponse.builder()
                .datasets(List.of(dashboardBubbleChartPinskdrevRu))
                .build();
    }
}
