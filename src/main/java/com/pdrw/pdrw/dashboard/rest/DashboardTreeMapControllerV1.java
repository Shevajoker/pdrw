package com.pdrw.pdrw.dashboard.rest;

import com.pdrw.pdrw.dashboard.entity.Dashboard;
import com.pdrw.pdrw.dashboard.entity.bubblechart.dto.DashboardBubbleChartResponse;
import com.pdrw.pdrw.dashboard.entity.linechart.dto.DashboardLineChartResponse;
import com.pdrw.pdrw.dashboard.entity.piechart.dto.DashboardPieChartResponse;
import com.pdrw.pdrw.dashboard.entity.treemap.dto.DashboardTreeMapResponse;
import com.pdrw.pdrw.dashboard.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final DashboardBubbleChartService dashboardBubbleChartService;

    @Operation(summary = "Get DashboardTreeMap", operationId = "getDashboardTreeMap")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got DashboardTreeMap", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = DashboardTreeMapResponse.class)))}),
            @ApiResponse(responseCode = "404", description = "Not found DashboardTreeMap", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Exception.class)))})
    })
    @GetMapping("/tree-map")
    public ResponseEntity<DashboardTreeMapResponse> getDashboardTreeMap() {
        DashboardTreeMapResponse result = dashboardTreeMapService.getDashboardTreeMapResponse();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get Dashboard Line Chart", operationId = "getDashboardLineChart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got DashboardLineChart", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = DashboardLineChartResponse.class)))}),
            @ApiResponse(responseCode = "404", description = "Not found DashboardLineChart", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Exception.class)))})
    })
    @GetMapping("/line-chart")
    public ResponseEntity<DashboardLineChartResponse> getDashboardLineChart() {
        DashboardLineChartResponse result = dashboardLineChartService.getDashboardLineChartResponse();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get DashboardPieChart", operationId = "getDashboardPieChart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got DashboardPieChart", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = DashboardPieChartResponse.class)))}),
            @ApiResponse(responseCode = "404", description = "Not found DashboardPieChart", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Exception.class)))})
    })
    @GetMapping("/pie-chart")
    public ResponseEntity<DashboardPieChartResponse> getDashboardPieChartResponse() {
        DashboardPieChartResponse result = dashboardPieChartService.getPieChart();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get Dashboard", operationId = "getDashboard")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got Dashboard", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Dashboard.class)))}),
            @ApiResponse(responseCode = "404", description = "Not found Dashboard", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Exception.class)))})
    })
    @GetMapping("/summary")
    public ResponseEntity<List<Dashboard>> getAll() {
        return new ResponseEntity<>(dashboardService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Get DashboardBubbleChart", operationId = "getDashboardBubbleChart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got DashboardBubbleChart", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = DashboardBubbleChartResponse.class)))}),
            @ApiResponse(responseCode = "404", description = "Not found DashboardBubbleChart", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Exception.class)))})
    })
    @GetMapping("/bubble-chart")
    public ResponseEntity<DashboardBubbleChartResponse> getDashboardBubbleChartResponse() {
        DashboardBubbleChartResponse result = dashboardBubbleChartService.getDashboardBubbleChart();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
