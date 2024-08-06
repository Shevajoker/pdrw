package com.pdrw.pdrw.hoff.rest;

import com.pdrw.pdrw.hoff.model.HoffItem;
import com.pdrw.pdrw.hoff.service.GetHoffDataService;
import com.pdrw.pdrw.hoff.service.HoffItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/hoff")
@RequiredArgsConstructor
@Tag(name = "HoffData")
public class GetHoffDataControllerV1 {

    private final GetHoffDataService getHoffDataService;
    private final HoffItemService hoffItemService;

    @Operation(summary = "Загрузить данные")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/set-data")
    public int setData(@RequestBody() String data) throws IOException {
        return getHoffDataService.setData(data);
    }

    @Operation(summary = "Найти по артикулу")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/list/{article}")
    public List<HoffItem> findByArticle(@PathVariable String article) throws IOException {
        return hoffItemService.findByArticleOrderByDateUpdateDesc(article);
    }


}
