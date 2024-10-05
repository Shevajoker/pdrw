package com.pdrw.pdrw.triya.rest;

import com.pdrw.pdrw.triya.model.Triya;
import com.pdrw.pdrw.triya.model.wrappers.TriyaAverageCategoryData;
import com.pdrw.pdrw.triya.service.TriyaDataService;
import com.pdrw.pdrw.triya.service.TriyaService;
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
@RequestMapping("/api/v1/triya")
@RequiredArgsConstructor
@Tag(name = "Triya")
public class TriyaDataRestControllerV1 {

    private final TriyaDataService triyaDataService;
    private final TriyaService triyaService;

    @Operation(summary = "Download data")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/set-data")
    public int setData(@RequestBody String data) {
        return triyaDataService.setData(data);
    }

    @Operation(summary = "Get all items")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/all")
    public List<Triya> getAll() {
        return triyaService.findAll();
    }

    @Operation(summary = "Get all items by article")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/article/{article}")
    public List<Triya> getByArticle(@PathVariable String article) {
        return triyaService.findByArticleOrderByDateUpdateDesc(article);
    }

    @Operation(summary = "Get all types")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/types")
    public List<String> getTypes() {
        return triyaService.findAllTypes();
    }

    @Operation(summary = "Get all by type")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/type/{type}")
    public List<Triya> getByType(@PathVariable String type) {
        return triyaService.findAllByType(type);
    }

    @Operation(summary = "Get average data by types")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/average-by-types")
    public ResponseEntity<List<TriyaAverageCategoryData>> getAverageCategoryData() {
        List<TriyaAverageCategoryData> result = triyaService.getTriyaAverageCategoryData();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get a new created items")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/new-created-items")
    public ResponseEntity<List<Triya>> getNewCreatedItems(
            @RequestParam(name = "limit", defaultValue = "20", required = false) Integer limit
    ) {
        List<Triya> result = triyaService.getNewCreatedItems(limit);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get a not updated items")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/not-updated-items")
    public ResponseEntity<List<Triya>> getNotUpdatedItems(
            @RequestParam(name = "limit", defaultValue = "20", required = false) Integer limit
    ) {
        List<Triya> result = triyaService.getNotUpdatedItems(limit);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get changed items")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/changed-items")
    public ResponseEntity<Map<String, List<Triya>>> getChangedItems(
            @RequestParam(name = "limit", defaultValue = "20", required = false) Integer limit
    ) {
        Map<String, List<Triya>> result = triyaService.getChangedItems(limit);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
