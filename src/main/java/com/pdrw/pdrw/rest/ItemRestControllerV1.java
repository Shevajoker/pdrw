package com.pdrw.pdrw.rest;

import com.pdrw.pdrw.model.Item;
import com.pdrw.pdrw.service.ItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemRestControllerV1 {

    private final ItemService itemService;

    public ItemRestControllerV1(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/find/{id}")
    public Item findById(@PathVariable String id) {
        return itemService.findById(id);
    }

    @GetMapping("/find/all")
    public List<Item> findAll() {
        return itemService.findAll();
    }
}
