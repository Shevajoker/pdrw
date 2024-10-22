package com.pdrw.pdrw.pinskdrevru.rest;

import com.pdrw.pdrw.pinskdrevru.model.PinskdrevRu;
import com.pdrw.pdrw.pinskdrevru.model.wrappers.PinskdrevRuAverageCategoryData;
import com.pdrw.pdrw.pinskdrevru.model.wrappers.PinskdrevRuData;
import com.pdrw.pdrw.pinskdrevru.service.PinskdrevRuDataService;
import com.pdrw.pdrw.pinskdrevru.service.PinskdrevRuService;
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
@RequestMapping("/api/v1/pinskdrev-ru")
@RequiredArgsConstructor
@Tag(name = "PinskdrevRu")
public class PinskdrevRuDataRestControllerV1 {

    private final PinskdrevRuDataService pinskdrevRuDataService;
    private final PinskdrevRuService pinskdrevRuService;

    @Operation(summary = "Download data")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/set-data")
    public int setData(@RequestBody String data) {
        return pinskdrevRuDataService.setData(data);
    }

    @Operation(summary = "Get all items")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/all")
    public List<PinskdrevRu> getAll() {
        return pinskdrevRuService.findAll();
    }

    @Operation(summary = "Get all items by article")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/article/{article}")
    public List<PinskdrevRu> getByArticle(@PathVariable String article) {
        return pinskdrevRuService.findByArticleOrderByDateUpdateDesc(article);
    }

    @Operation(summary = "Get all types")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/types")
    public List<String> getTypes() {
        return pinskdrevRuService.findAllTypes();
    }

    @Operation(summary = "Get all by type")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/type/{type}")
    public List<PinskdrevRu> getByType(@PathVariable String type) {
        return pinskdrevRuService.findAllByType(type);
    }

    @Operation(summary = "Get data for front")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/data")
    public ResponseEntity<PinskdrevRuData> getData() {
        PinskdrevRuData result = pinskdrevRuService.getData();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get average data by types")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/average-by-types")
    public ResponseEntity<List<PinskdrevRuAverageCategoryData>> getAverageCategoryData() {
        List<PinskdrevRuAverageCategoryData> result = pinskdrevRuService.getPinskdrevRuAverageCategoryData();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get a new created items")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/new-created-items")
    public ResponseEntity<List<PinskdrevRu>> getNewCreatedItems(
            @RequestParam(name = "limit", defaultValue = "20", required = false) Integer limit
    ) {
        List<PinskdrevRu> result = pinskdrevRuService.getNewCreatedItems(limit);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get a not updated items")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/not-updated-items")
    public ResponseEntity<List<PinskdrevRu>> getNotUpdatedItems(
            @RequestParam(name = "limit", defaultValue = "20", required = false) Integer limit
    ) {
        List<PinskdrevRu> result = pinskdrevRuService.getNotUpdatedItems(limit);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get changed items")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/changed-items")
    public ResponseEntity<Map<String, List<PinskdrevRu>>> getChangedItems(
            @RequestParam(name = "limit", defaultValue = "20", required = false) Integer limit
    ) {
        Map<String, List<PinskdrevRu>> result = pinskdrevRuService.getChangedItems(limit);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
