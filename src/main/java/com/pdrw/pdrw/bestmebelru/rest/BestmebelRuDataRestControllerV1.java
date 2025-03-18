package com.pdrw.pdrw.bestmebelru.rest;

import com.pdrw.pdrw.bestmebelru.model.BestmebelRu;
import com.pdrw.pdrw.bestmebelru.model.wrappers.BestmebelRuAverageCategoryData;
import com.pdrw.pdrw.bestmebelru.model.wrappers.BestmebelRuData;
import com.pdrw.pdrw.bestmebelru.service.BestmebelRuDataService;
import com.pdrw.pdrw.bestmebelru.service.BestmebelRuService;
import com.pdrw.pdrw.dashboard.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/bestmebel-ru")
@RequiredArgsConstructor
@Tag(name = "BestmebelRu")
public class BestmebelRuDataRestControllerV1 {

    private final BestmebelRuDataService bestmebelRuDataService;
    private final BestmebelRuService bestmebelRuService;
    private final DashboardService dashboardService;

    @Operation(summary = "Download data")
    @PostMapping("/set-data")
    public int setData(@RequestBody String data) {
        int i = bestmebelRuDataService.setData(data);
        List<BestmebelRu> all = bestmebelRuService.findAll();
//        dashboardService.createDashboardBestmebelRu(all);
        return i;
    }

    @Operation(summary = "Get all items")
    @GetMapping("/all")
    public List<BestmebelRu> getAll() {
        return bestmebelRuService.findAll();
    }

    @Operation(summary = "Get all items by article")
    @GetMapping("/article/{article}")
    public List<BestmebelRu> getByArticle(@PathVariable String article) {
        return bestmebelRuService.findByArticleOrderByDateUpdateDesc(article);
    }

    @Operation(summary = "Get all types")
    @GetMapping("/types")
    public List<String> getTypes() {
        return bestmebelRuService.findAllTypes();
    }

    @Operation(summary = "Get all by type")
    @GetMapping("/type/{type}")
    public List<BestmebelRu> getByType(@PathVariable String type) {
        return bestmebelRuService.findAllByType(type);
    }

    @Operation(summary = "Get data for front")
    @GetMapping("/data")
    public ResponseEntity<BestmebelRuData> getData() {
        BestmebelRuData result = bestmebelRuService.getData();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get average data by types")
    @GetMapping("/average-by-types")
    public ResponseEntity<List<BestmebelRuAverageCategoryData>> getAverageCategoryData() {
        List<BestmebelRuAverageCategoryData> result = bestmebelRuService.getBestmebelRuAverageCategoryData();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get a new created items")
    @GetMapping("/new-created-items")
    public ResponseEntity<List<BestmebelRu>> getNewCreatedItems(
            @RequestParam(name = "limit", defaultValue = "20", required = false) Integer limit
    ) {
        List<BestmebelRu> result = bestmebelRuService.getNewCreatedItems(limit);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get a not updated items")
    @GetMapping("/not-updated-items")
    public ResponseEntity<List<BestmebelRu>> getNotUpdatedItems(
            @RequestParam(name = "limit", defaultValue = "20", required = false) Integer limit
    ) {
        List<BestmebelRu> result = bestmebelRuService.getNotUpdatedItems(limit);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get changed items")
    @GetMapping("/changed-items")
    public ResponseEntity<Map<String, List<BestmebelRu>>> getChangedItems(
            @RequestParam(name = "limit", defaultValue = "20", required = false) Integer limit
    ) {
        Map<String, List<BestmebelRu>> result = bestmebelRuService.getChangedItems(limit);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
