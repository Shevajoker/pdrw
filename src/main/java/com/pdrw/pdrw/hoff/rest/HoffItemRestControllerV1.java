package com.pdrw.pdrw.hoff.rest;

import com.pdrw.pdrw.hoff.model.HoffItem;
import com.pdrw.pdrw.hoff.service.HoffItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
@Tag(name = "Data")
public class HoffItemRestControllerV1 {

    private final HoffItemService hoffItemService;

    @GetMapping("/find/{id}")
    public HoffItem findById(@PathVariable UUID id) {
        return hoffItemService.findById(id);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/find/all")
    public List<HoffItem> findAll() {
        return hoffItemService.findAll();
    }
}
