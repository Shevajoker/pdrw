package com.pdrw.pdrw.rest;

import com.pdrw.pdrw.model.Item;
import com.pdrw.pdrw.service.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
@Tag(name = "Data")
public class ItemRestControllerV1 {

    private final ItemService itemService;

    @GetMapping("/find/{id}")
    public Item findById(@PathVariable String id) {
        return itemService.findById(id);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/find/all")
    public List<Item> findAll() {
        return itemService.findAll();
    }
}
