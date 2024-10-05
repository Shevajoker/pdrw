package com.pdrw.pdrw.pinskdrev.rest;

import com.pdrw.pdrw.pinskdrev.model.Pinskdrev;
import com.pdrw.pdrw.pinskdrev.model.wrappers.PinskdrevAverageCategoryData;
import com.pdrw.pdrw.pinskdrev.model.wrappers.PinskdrevData;
import com.pdrw.pdrw.pinskdrev.service.PinskdrevDataService;
import com.pdrw.pdrw.pinskdrev.service.PinskdrevService;
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
@RequestMapping("/api/v1/pinskdrev")
@RequiredArgsConstructor
@Tag(name = "Pinskdrev")
public class PinskdrevDataRestControllerV1 {

    private final PinskdrevDataService pinskdrevDataService;
    private final PinskdrevService pinskdrevService;

    @Operation(summary = "Download data")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/set-data")
    public int setData(@RequestBody String data) {
        return pinskdrevDataService.setData(data);
    }

    @Operation(summary = "Get all items")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/all")
    public List<Pinskdrev> getAll() {
        return pinskdrevService.findAll();
    }

    @Operation(summary = "Get all items by article")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/article/{article}")
    public List<Pinskdrev> getByArticle(@PathVariable String article) {
        return pinskdrevService.findByArticleOrderByDateUpdateDesc(article);
    }

    @Operation(summary = "Get all types")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/types")
    public List<String> getTypes() {
        return pinskdrevService.findAllTypes();
    }

    @Operation(summary = "Get all by type")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/type/{type}")
    public List<Pinskdrev> getByType(@PathVariable String type) {
        return pinskdrevService.findAllByType(type);
    }

    @Operation(summary = "Get data for front")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/data")
    public ResponseEntity<PinskdrevData> getData() {
        PinskdrevData result = pinskdrevService.getData();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get average data by types")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/average-by-types")
    public ResponseEntity<List<PinskdrevAverageCategoryData>> getAverageCategoryData() {
        List<PinskdrevAverageCategoryData> result = pinskdrevService.getPinskdrevAverageCategoryData();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get a new created items")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/new-created-items")
    public ResponseEntity<List<Pinskdrev>> getNewCreatedItems(
            @RequestParam(name = "limit", defaultValue = "20", required = false) Integer limit
    ) {
        List<Pinskdrev> result = pinskdrevService.getNewCreatedItems(limit);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get a not updated items")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/not-updated-items")
    public ResponseEntity<List<Pinskdrev>> getNotUpdatedItems(
            @RequestParam(name = "limit", defaultValue = "20", required = false) Integer limit
    ) {
        List<Pinskdrev> result = pinskdrevService.getNotUpdatedItems(limit);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get changed items")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/changed-items")
    public ResponseEntity<Map<String, List<Pinskdrev>>> getChangedItems(
            @RequestParam(name = "limit", defaultValue = "20", required = false) Integer limit
    ) {
        Map<String, List<Pinskdrev>> result = pinskdrevService.getChangedItems(limit);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
