package com.pdrw.pdrw.triya.rest;

import com.pdrw.pdrw.dashboard.service.DashboardService;
import com.pdrw.pdrw.triya.model.TriyaRu;
import com.pdrw.pdrw.triya.model.wrappers.TriyaRuAverageCategoryData;
import com.pdrw.pdrw.triya.model.wrappers.TriyaRuData;
import com.pdrw.pdrw.triya.service.TriyaRuDataService;
import com.pdrw.pdrw.triya.service.TriyaRuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/triya-ru")
@RequiredArgsConstructor
@Tag(name = "TriyaRu")
public class TriyaRuDataRestControllerV1 {

    private final TriyaRuDataService dataService;
    private final TriyaRuService triyaRuService;
    private final DashboardService dashboardService;

    @Operation(summary = "Download data")
    @PostMapping("/set-data")
    public int setData(@RequestBody String data) {
        int i = dataService.setData(data);
        List<TriyaRu> all = triyaRuService.findAll();
        dashboardService.createDashboardTriya(all);
        return i;
    }

    @Operation(summary = "Get all items")
    @GetMapping("/all")
    public List<TriyaRu> getAll() {
        return triyaRuService.findAll();
    }

    @Operation(summary = "Get all items by article")
    @GetMapping("/article/{article}")
    public List<TriyaRu> getByArticle(@PathVariable String article) {
        return triyaRuService.findByArticleOrderByDateUpdateDesc(article);
    }

    @Operation(summary = "Get all types")
    @GetMapping("/types")
    public List<String> getTypes() {
        return triyaRuService.findAllTypes();
    }

    @Operation(summary = "Get all by type")
    @GetMapping("/type/{type}")
    public List<TriyaRu> getByType(@PathVariable String type) {
        return triyaRuService.findAllByType(type);
    }

    @Operation(summary = "Get data for front")
    @GetMapping("/data")
    public ResponseEntity<TriyaRuData> getData() {
        TriyaRuData result = triyaRuService.getData();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get average data by types")
    @GetMapping("/average-by-types")
    public ResponseEntity<List<TriyaRuAverageCategoryData>> getAverageCategoryData() {
        List<TriyaRuAverageCategoryData> result = triyaRuService.getTriyaRuAverageCategoryData();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get a new created items")
    @GetMapping("/new-created-items")
    public ResponseEntity<List<TriyaRu>> getNewCreatedItems(
            @RequestParam(name = "limit", defaultValue = "20", required = false) Integer limit
    ) {
        List<TriyaRu> result = triyaRuService.getNewCreatedItems(limit);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get a not updated items")
    @GetMapping("/not-updated-items")
    public ResponseEntity<List<TriyaRu>> getNotUpdatedItems(
            @RequestParam(name = "limit", defaultValue = "20", required = false) Integer limit
    ) {
        List<TriyaRu> result = triyaRuService.getNotUpdatedItems(limit);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get changed items")
    @GetMapping("/changed-items")
    public ResponseEntity<Map<String, List<TriyaRu>>> getChangedItems(
            @RequestParam(name = "limit", defaultValue = "20", required = false) Integer limit
    ) {
        Map<String, List<TriyaRu>> result = triyaRuService.getChangedItems(limit);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
