package com.pdrw.pdrw.triya.rest;

import com.pdrw.pdrw.triya.charts.boxchart.BoxChartResponse;
import com.pdrw.pdrw.triya.charts.piechart.PieChartResponse;
import com.pdrw.pdrw.triya.charts.verticalbarchart.VerticalBarChartResponse;
import com.pdrw.pdrw.triya.service.BoxChartTriyaRuService;
import com.pdrw.pdrw.triya.service.PieChartTriyaRuService;
import com.pdrw.pdrw.triya.service.VerticalBarChartTriyaRuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "TriyaRu")
@RequestMapping("/api/v1/triya-ru/charts")
public class ChartsTriyaRuRestControllerV1 {

    private final BoxChartTriyaRuService boxChartService;
    private final PieChartTriyaRuService pieChartTriyaRuService;
    private final VerticalBarChartTriyaRuService verticalBarChartTriyaRuService;

    @Operation(summary = "Get data for Box Chart")
    @GetMapping("/box-chart")
    public ResponseEntity<BoxChartResponse> boxChart() {
        return ResponseEntity.ok(boxChartService.getBoxChart());
    }

    @Operation(summary = "Get data for Vertical Bar Chart")
    @GetMapping("/vertical-bar-chart")
    public ResponseEntity<VerticalBarChartResponse> verticalBarChart() {
        return ResponseEntity.ok(verticalBarChartTriyaRuService.getVerticalBarChart());
    }

    @Operation(summary = "Get data for Grouped Pie Chart")
    @GetMapping("/pie-chart")
    public ResponseEntity<PieChartResponse> pieChart() {
        return ResponseEntity.ok(pieChartTriyaRuService.getPieChart());
    }
}
