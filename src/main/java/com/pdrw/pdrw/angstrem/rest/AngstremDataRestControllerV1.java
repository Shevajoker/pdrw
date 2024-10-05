package com.pdrw.pdrw.angstrem.rest;

import com.pdrw.pdrw.angstrem.model.Angstrem;
import com.pdrw.pdrw.angstrem.model.wrappers.AngstremAverageCategoryData;
import com.pdrw.pdrw.angstrem.service.AngstremDataService;
import com.pdrw.pdrw.angstrem.service.AngstremService;
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
@RequestMapping("/api/v1/angstrem")
@RequiredArgsConstructor
@Tag(name = "Angstrem")
public class AngstremDataRestControllerV1 {

    private final AngstremDataService angstremDataService;
    private final AngstremService angstremService;

    @Operation(summary = "Download data")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/set-data")
    public int setData(@RequestBody String data) {
        return angstremDataService.setData(data);
    }

    @Operation(summary = "Get all items")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/all")
    public List<Angstrem> getAll() {
        return angstremService.findAll();
    }

    @Operation(summary = "Get all items by article")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/article/{article}")
    public List<Angstrem> getByArticle(@PathVariable String article) {
        return angstremService.findByArticleOrderByDateUpdateDesc(article);
    }

    @Operation(summary = "Get all types")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/types")
    public List<String> getTypes() {
        return angstremService.findAllTypes();
    }

    @Operation(summary = "Get all by type")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/type/{type}")
    public List<Angstrem> getByType(@PathVariable String type) {
        return angstremService.findAllByType(type);
    }

    @Operation(summary = "Get average data by types")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/average-by-types")
    public ResponseEntity<List<AngstremAverageCategoryData>> getAverageCategoryData() {
        List<AngstremAverageCategoryData> result = angstremService.getAngstremAverageCategoryData();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get a new created items")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/new-created-items")
    public ResponseEntity<List<Angstrem>> getNewCreatedItems(
            @RequestParam(name = "limit", defaultValue = "20", required = false) Integer limit
    ) {
        List<Angstrem> result = angstremService.getNewCreatedItems(limit);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get a not updated items")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/not-updated-items")
    public ResponseEntity<List<Angstrem>> getNotUpdatedItems(
            @RequestParam(name = "limit", defaultValue = "20", required = false) Integer limit
    ) {
        List<Angstrem> result = angstremService.getNotUpdatedItems(limit);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get changed items")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/changed-items")
    public ResponseEntity<Map<String, List<Angstrem>>> getChangedItems(
            @RequestParam(name = "limit", defaultValue = "20", required = false) Integer limit
    ) {
        Map<String, List<Angstrem>> result = angstremService.getChangedItems(limit);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
