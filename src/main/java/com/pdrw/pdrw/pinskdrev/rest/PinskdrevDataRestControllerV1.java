package com.pdrw.pdrw.pinskdrev.rest;

import com.pdrw.pdrw.pinskdrev.model.Pinskdrev;
import com.pdrw.pdrw.pinskdrev.model.wrappers.PinskdrevData;
import com.pdrw.pdrw.pinskdrev.service.PinskdrevDataService;
import com.pdrw.pdrw.pinskdrev.service.PinskdrevService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pinskdrev")
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
    public PinskdrevData getData() {
        return pinskdrevService.getData();
    }
}
