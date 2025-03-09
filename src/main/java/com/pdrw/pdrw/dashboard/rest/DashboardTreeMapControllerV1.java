package com.pdrw.pdrw.dashboard.rest;

import com.pdrw.pdrw.dashboard.entity.Dashboard;
import com.pdrw.pdrw.dashboard.entity.linechart.dto.DashboardLineChartResponse;
import com.pdrw.pdrw.dashboard.entity.piechart.dto.DashboardPieChartResponse;
import com.pdrw.pdrw.dashboard.entity.treemap.dto.DashboardTreeMapResponse;
import com.pdrw.pdrw.dashboard.service.DashboardLineChartService;
import com.pdrw.pdrw.dashboard.service.DashboardPieChartService;
import com.pdrw.pdrw.dashboard.service.DashboardService;
import com.pdrw.pdrw.dashboard.service.DashboardTreeMapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard")
public class DashboardTreeMapControllerV1 {

    private final DashboardTreeMapService dashboardTreeMapService;
    private final DashboardLineChartService dashboardLineChartService;
    private final DashboardService dashboardService;
    private final DashboardPieChartService dashboardPieChartService;

    @Operation(summary = "Get DashboardTreeMap")
    @GetMapping("/tree-map")
    public ResponseEntity<DashboardTreeMapResponse> getDashboardTreeMap() {
        DashboardTreeMapResponse result = dashboardTreeMapService.getDashboardTreeMapResponse();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get DashboardLineChart")
    @GetMapping("/line-chart")
    public ResponseEntity<DashboardLineChartResponse> getDashboardLineChart() {
        DashboardLineChartResponse result = dashboardLineChartService.getDashboardLineChartResponse();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get DashboardPieChart")
    @GetMapping("/pie-chart")
    public ResponseEntity<DashboardPieChartResponse> getDashboardPieChartResponse() {
        DashboardPieChartResponse result = dashboardPieChartService.getPieChart();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get Dashboard")
    @GetMapping("/summary")
    public ResponseEntity<List<Dashboard>> getAll() {
        return new ResponseEntity<>(dashboardService.findAll(), HttpStatus.OK);
    }
}
