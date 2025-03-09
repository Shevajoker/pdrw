package com.pdrw.pdrw.bestmebelru.rest;

import com.pdrw.pdrw.bestmebelru.charts.boxchart.BoxChartResponse;
import com.pdrw.pdrw.bestmebelru.charts.piechart.PieChartResponse;
import com.pdrw.pdrw.bestmebelru.charts.verticalbarchart.VerticalBarChartResponse;
import com.pdrw.pdrw.bestmebelru.service.BoxChartBestmebelRuService;
import com.pdrw.pdrw.bestmebelru.service.PieChartBestmebelRuService;
import com.pdrw.pdrw.bestmebelru.service.VerticalBarChartBestmebelRuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "BestmebelRu")
@RequestMapping("/api/v1/bestmebel-ru/charts")
public class ChartsBestmebelRuRestControllerV1 {

    private final BoxChartBestmebelRuService boxChartBestmebelRuService;
    private final PieChartBestmebelRuService pieChartBestmebelRuService;
    private final VerticalBarChartBestmebelRuService verticalBarChartBestmebelRuService;

    @Operation(summary = "Get data for Box Chart")
    @GetMapping("/box-chart")
    public ResponseEntity<BoxChartResponse> boxChart() {
        return ResponseEntity.ok(boxChartBestmebelRuService.getBoxChart());
    }

    @Operation(summary = "Get data for Vertical Bar Chart")
    @GetMapping("/vertical-bar-chart")
    public ResponseEntity<VerticalBarChartResponse> verticalBarChart() {
        return ResponseEntity.ok(verticalBarChartBestmebelRuService.getVerticalBarChart());
    }

    @Operation(summary = "Get data for Grouped Pie Chart")
    @GetMapping("/pie-chart")
    public ResponseEntity<PieChartResponse> pieChart() {
        return ResponseEntity.ok(pieChartBestmebelRuService.getPieChart());
    }
}
