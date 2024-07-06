package com.pdrw.pdrw.service.impl;

import com.pdrw.pdrw.model.Item;
import com.pdrw.pdrw.repository.ItemRepository;
import com.pdrw.pdrw.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    @Override
    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item findById(String id) {
        return itemRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }
}
