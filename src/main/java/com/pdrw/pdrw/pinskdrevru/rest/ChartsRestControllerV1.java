package com.pdrw.pdrw.pinskdrevru.rest;

import com.pdrw.pdrw.pinskdrevru.charts.boxchart.BoxChartResponse;
import com.pdrw.pdrw.pinskdrevru.charts.piechart.PieChartResponse;
import com.pdrw.pdrw.pinskdrevru.charts.verticalbarchart.VerticalBarChartResponse;
import com.pdrw.pdrw.pinskdrevru.service.BoxChartService;
import com.pdrw.pdrw.pinskdrevru.service.PieChartService;
import com.pdrw.pdrw.pinskdrevru.service.VerticalBarChartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "PinskdrevRu")
@RequestMapping("/api/v1/pinskdrev-ru/charts")
public class ChartsRestControllerV1 {

    private final BoxChartService boxChartService;
    private final PieChartService pieChartService;
    private final VerticalBarChartService verticalBarChartService;

    @Operation(summary = "Get data for Box Chart")
    @GetMapping("/box-chart")
    public ResponseEntity<BoxChartResponse> boxChart() {
        return ResponseEntity.ok(boxChartService.getBoxChart());
    }

    @Operation(summary = "Get data for Vertical Bar Chart")
    @GetMapping("/vertical-bar-chart")
    public ResponseEntity<VerticalBarChartResponse> verticalBarChart() {
        return ResponseEntity.ok(verticalBarChartService.getVerticalBarChart());
    }

    @Operation(summary = "Get data for Grouped Pie Chart")
    @GetMapping("/pie-chart")
    public ResponseEntity<PieChartResponse> pieChart() {
        return ResponseEntity.ok(pieChartService.getPieChart());
    }
}
