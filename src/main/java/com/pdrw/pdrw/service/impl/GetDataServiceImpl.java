package com.pdrw.pdrw.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.pdrw.pdrw.loader.DataLoader;
import com.pdrw.pdrw.model.Item;
import com.pdrw.pdrw.service.GetDataService;
import com.pdrw.pdrw.service.ItemService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class GetDataServiceImpl implements GetDataService {

    private final ItemService itemService;

    public GetDataServiceImpl(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public int setData(String data) throws IOException {
        return 0;
    }

    @Override
    public List<Item> getData(Integer limit) {
        return itemService.findAll();
    }
}
