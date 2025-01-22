package com.pdrw.pdrw.triya.service.impl;

import com.pdrw.pdrw.triya.charts.piechart.PieChartResponse;
import com.pdrw.pdrw.triya.service.TriyaRuService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DisplayName("Тест сервиса PieChartTriyaRuServiceImpl")
@ExtendWith(MockitoExtension.class)
class PieChartTriyaRuServiceImplTest {

    @InjectMocks
    private PieChartTriyaRuServiceImpl pieChartTriyaRuService;
    @Mock
    private TriyaRuService triyaRuService;
    private static final String TYPE = "стол";
    private static final Integer VALUE = 1;

    @Test
    @DisplayName("Возвращаем PieChartResponse ")
    void getPieChart_returnCorrectPieChart() {
        when(triyaRuService.findAllTypes()).thenReturn(List.of(TYPE));
        when(triyaRuService.countItemsByType(TYPE)).thenReturn(VALUE);

        PieChartResponse actual = pieChartTriyaRuService.getPieChart();

        System.out.println();

        assertAll(
                () -> assertEquals(VALUE, actual.getPieChartList().size()),
                () -> assertEquals(TYPE, actual.getPieChartList().getFirst().getName()),
                () -> assertEquals(VALUE, actual.getPieChartList().getFirst().getValue())
        );
    }
}
