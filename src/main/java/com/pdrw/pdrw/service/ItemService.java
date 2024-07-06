package com.pdrw.pdrw.service;

import com.pdrw.pdrw.model.Item;

import java.util.List;

public interface ItemService {

    Item createItem(Item item);
    Item findById(String id);

    List<Item> findAll();
}
