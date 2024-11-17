package com.pdrw.pdrw.pinskdrevru.rest;

import com.pdrw.pdrw.pinskdrevru.charts.boxchart.BoxChartResponse;
import com.pdrw.pdrw.pinskdrevru.charts.groupedverticalbarchart.GroupedVerticalBarChartResponse;
import com.pdrw.pdrw.pinskdrevru.charts.piechart.PieChartResponse;
import com.pdrw.pdrw.pinskdrevru.service.BoxChartService;
import com.pdrw.pdrw.pinskdrevru.service.GroupedVerticalBarChartService;
import com.pdrw.pdrw.pinskdrevru.service.PieChartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "PinskdrevRu")
@RequestMapping("/api/v1/pinskdrev-ru/charts")
public class ChartsRestControllerV1 {

    private final BoxChartService boxChartService;
    private final GroupedVerticalBarChartService groupedVerticalBarChartService;
    private final PieChartService pieChartService;

    @Operation(summary = "Get data for Box Chart")
    @GetMapping("/box-chart")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BoxChartResponse> boxChart() {
        return ResponseEntity.ok(boxChartService.getBoxChart());
    }

    @Operation(summary = "Get data for Grouped Vertical Bar Chart")
    @GetMapping("/grouped-vertical-bar-chart")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GroupedVerticalBarChartResponse> groupedVerticalBarChart() {
        return ResponseEntity.ok(groupedVerticalBarChartService.getGroupedVerticalBarChart());
    }

    @Operation(summary = "Get data for Grouped Pie Chart")
    @GetMapping("/pie-chart")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PieChartResponse> pieChart() {
        return ResponseEntity.ok(pieChartService.getPieChart());
    }
}
