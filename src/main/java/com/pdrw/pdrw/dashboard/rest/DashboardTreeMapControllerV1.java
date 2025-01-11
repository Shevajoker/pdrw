package com.pdrw.pdrw.dashboard.rest;

import com.pdrw.pdrw.dashboard.entity.treemap.dto.DashboardTreeMapResponse;
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

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard")
public class DashboardTreeMapControllerV1 {

    private final DashboardTreeMapService dashboardTreeMapService;

    @Operation(summary = "Get DashboardTreeMap")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tree-map")
    public ResponseEntity<DashboardTreeMapResponse> getDashboardTreeMap() {
        DashboardTreeMapResponse result = dashboardTreeMapService.getDashboardTreeMapResponse();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
