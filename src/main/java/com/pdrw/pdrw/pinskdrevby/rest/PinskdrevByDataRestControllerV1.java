package com.pdrw.pdrw.pinskdrevby.rest;

import com.pdrw.pdrw.pinskdrevby.model.PinskdrevBy;
import com.pdrw.pdrw.pinskdrevby.model.wrappers.PinskdrevByAverageCategoryData;
import com.pdrw.pdrw.pinskdrevby.model.wrappers.PinskdrevByData;
import com.pdrw.pdrw.pinskdrevby.service.PinskdrevByDataService;
import com.pdrw.pdrw.pinskdrevby.service.PinskdrevByService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/pinskdrev-by")
@RequiredArgsConstructor
@Tag(name = "PinskdrevBy")
public class PinskdrevByDataRestControllerV1 {

    private final PinskdrevByDataService pinskdrevByDataService;
    private final PinskdrevByService pinskdrevByService;

    @Operation(summary = "Download data")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/set-data")
    public int setData(@RequestBody String data) {
        return pinskdrevByDataService.setData(data);
    }

    @Operation(summary = "Get all items")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/all")
    public List<PinskdrevBy> getAll() {
        return pinskdrevByService.findAll();
    }

    @Operation(summary = "Get all items by article")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/article/{article}")
    public List<PinskdrevBy> getByArticle(@PathVariable String article) {
        return pinskdrevByService.findByArticleOrderByDateUpdateDesc(article);
    }

    @Operation(summary = "Get all types")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/types")
    public List<String> getTypes() {
        return pinskdrevByService.findAllTypes();
    }

    @Operation(summary = "Get all by type")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/type/{type}")
    public List<PinskdrevBy> getByType(@PathVariable String type) {
        return pinskdrevByService.findAllByType(type);
    }

    @Operation(summary = "Get data for front")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/data")
    public ResponseEntity<PinskdrevByData> getData() {
        PinskdrevByData result = pinskdrevByService.getData();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get average data by types")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/average-by-types")
    public ResponseEntity<List<PinskdrevByAverageCategoryData>> getAverageCategoryData() {
        List<PinskdrevByAverageCategoryData> result = pinskdrevByService.getPinskdrevByAverageCategoryData();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get a new created items")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/new-created-items")
    public ResponseEntity<List<PinskdrevBy>> getNewCreatedItems(
            @RequestParam(name = "limit", defaultValue = "20", required = false) Integer limit
    ) {
        List<PinskdrevBy> result = pinskdrevByService.getNewCreatedItems(limit);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get a not updated items")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/not-updated-items")
    public ResponseEntity<List<PinskdrevBy>> getNotUpdatedItems(
            @RequestParam(name = "limit", defaultValue = "20", required = false) Integer limit
    ) {
        List<PinskdrevBy> result = pinskdrevByService.getNotUpdatedItems(limit);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get changed items")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/changed-items")
    public ResponseEntity<Map<String, List<PinskdrevBy>>> getChangedItems(
            @RequestParam(name = "limit", defaultValue = "20", required = false) Integer limit
    ) {
        Map<String, List<PinskdrevBy>> result = pinskdrevByService.getChangedItems(limit);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
