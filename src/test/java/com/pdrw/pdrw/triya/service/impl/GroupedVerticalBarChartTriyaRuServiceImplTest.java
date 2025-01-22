package com.pdrw.pdrw.triya.service.impl;

import com.pdrw.pdrw.triya.charts.groupedverticalbarchart.GroupedVerticalBarChartResponse;
import com.pdrw.pdrw.triya.model.TriyaRu;
import com.pdrw.pdrw.triya.service.TriyaRuService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Тест сервиса GroupedVerticalBarChartTriyaRuServiceImpl")
@ExtendWith(MockitoExtension.class)
class GroupedVerticalBarChartTriyaRuServiceImplTest {

    @InjectMocks
    private GroupedVerticalBarChartTriyaRuServiceImpl groupedVerticalBarChartTriyaRuService;
    @Mock
    private TriyaRuService triyaRuService;
    private static final String TYPE = "стол";

    @Test
    @DisplayName("Возвращаем GroupedVerticalBarChartResponse")
    void getGroupedVerticalBarChart_returnCorrectGroupedVerticalBarChart() {
        TriyaRu triyaRu = new TriyaRu(UUID.randomUUID(), "article", "name", "image", new BigDecimal(6), new BigDecimal(5),
                new BigDecimal(1), new Date(), new Date(), "type", 1, 1, 1, 1, 1.0, true, "link");

        when(triyaRuService.findAllTypes()).thenReturn(List.of(TYPE));
        when(triyaRuService.findActualWithSaleByType(TYPE)).thenReturn(List.of(triyaRu));


        GroupedVerticalBarChartResponse actual = groupedVerticalBarChartTriyaRuService.getGroupedVerticalBarChart();

        assertAll(
                () -> assertEquals(1, actual.getGroupedVerticalBarChartsDto().size()),
                () -> assertEquals("стол", actual.getGroupedVerticalBarChartsDto().getFirst().getType()),
                () -> assertEquals(1, actual.getGroupedVerticalBarChartsDto().getFirst().getGroupedVerticalBarCharts().size()),
                () -> assertEquals("new_price", actual.getGroupedVerticalBarChartsDto().getFirst().getGroupedVerticalBarCharts().getFirst().getSeries().getFirst().getName()),
                () -> assertEquals("old_price", actual.getGroupedVerticalBarChartsDto().getFirst().getGroupedVerticalBarCharts().getFirst().getSeries().getLast().getName()),
                () -> assertEquals(BigDecimal.valueOf(6), actual.getGroupedVerticalBarChartsDto().getFirst().getGroupedVerticalBarCharts().getFirst().getSeries().getFirst().getValue()),
                () -> assertEquals(BigDecimal.valueOf(5), actual.getGroupedVerticalBarChartsDto().getFirst().getGroupedVerticalBarCharts().getFirst().getSeries().getLast().getValue())
        );
    }
}
