package com.pdrw.pdrw.triya.service.impl;

import com.pdrw.pdrw.triya.charts.boxchart.BoxChart;
import com.pdrw.pdrw.triya.charts.boxchart.BoxChartResponse;
import com.pdrw.pdrw.triya.charts.boxchart.Series;
import com.pdrw.pdrw.triya.model.TriyaRu;
import com.pdrw.pdrw.triya.service.TriyaRuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Тест сервиса BoxChartTriyaRuServiceImpl")
@ExtendWith(MockitoExtension.class)
class BoxChartTriyaRuServiceImplTest {

    @InjectMocks
    private BoxChartTriyaRuServiceImpl boxChartTriyaRuService;
    @Mock
    private TriyaRuService triyaRuService;
    @Mock
    private TriyaRu one;
    @Mock
    private TriyaRu two;
    private List<TriyaRu> items;
    private BoxChartResponse expected;

    private static final String TYPE = "стол";
    private static final String ONE_PRICE = "10";
    private static final String TWO_PRICE = "6";

    @BeforeEach
    void setUp() {
        items = new ArrayList<>();
        items.add(one);
        items.add(two);

        expected = new BoxChartResponse();
        BoxChart boxChart = new BoxChart();
        boxChart.setName(TYPE);
        boxChart.setSeries(List.of(new Series("Q1", new BigDecimal(ONE_PRICE)), new Series("Q2", new BigDecimal("8.00")),
                new Series("Q3", new BigDecimal(TWO_PRICE)), new Series("min", new BigDecimal(ONE_PRICE)),
                new Series("max", new BigDecimal(TWO_PRICE))));
        expected.getCharts().add(boxChart);
    }

    @Test
    @DisplayName("Получаем корректный BoxChartResponse")
    void getBoxChart_returnBoxChartResponse() {
        when(triyaRuService.findAllTypes()).thenReturn(List.of(TYPE));
        when(triyaRuService.findActualByType(TYPE)).thenReturn(items);
        when(one.getPriceNew()).thenReturn(new BigDecimal(ONE_PRICE));
        when(two.getPriceNew()).thenReturn(new BigDecimal(TWO_PRICE));

        BoxChartResponse actual = boxChartTriyaRuService.getBoxChart();

        assertEquals(expected, actual);
    }
}
