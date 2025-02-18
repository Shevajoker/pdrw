package com.pdrw.pdrw.pinskdrevby.rest;

import com.pdrw.pdrw.pinskdrevby.charts.boxchart.BoxChartResponse;
import com.pdrw.pdrw.pinskdrevby.charts.piechart.PieChartResponse;
import com.pdrw.pdrw.pinskdrevby.charts.verticalbarchart.VerticalBarChartResponse;
import com.pdrw.pdrw.pinskdrevby.service.BoxChartPinskdrevByService;
import com.pdrw.pdrw.pinskdrevby.service.PieChartPinskdrevByService;
import com.pdrw.pdrw.pinskdrevby.service.VerticalBarChartPinskdrevByService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "PinskdrevBy")
@RequestMapping("/api/v1/pinskdrev-by/charts")
public class ChartsPinskdrevByRestControllerV1 {

    private final BoxChartPinskdrevByService boxChartService;
    private final PieChartPinskdrevByService pieChartPinskdrevByService;
    private final VerticalBarChartPinskdrevByService verticalBarChartPinskdrevByService;

    @Operation(summary = "Get data for Box Chart")
    @GetMapping("/box-chart")
    public ResponseEntity<BoxChartResponse> boxChart() {
        return ResponseEntity.ok(boxChartService.getBoxChart());
    }

    @Operation(summary = "Get data for Vertical Bar Chart")
    @GetMapping("/vertical-bar-chart")
    public ResponseEntity<VerticalBarChartResponse> verticalBarChart() {
        return ResponseEntity.ok(verticalBarChartPinskdrevByService.getVerticalBarChart());
    }

    @Operation(summary = "Get data for Grouped Pie Chart")
    @GetMapping("/pie-chart")
    public ResponseEntity<PieChartResponse> pieChart() {
        return ResponseEntity.ok(pieChartPinskdrevByService.getPieChart());
    }
}
