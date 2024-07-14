package com.pdrw.pdrw.hoff.service;

import com.pdrw.pdrw.hoff.model.HoffItem;

import java.util.List;
import java.util.UUID;

public interface HoffItemService {

    HoffItem createItem(HoffItem item);
    HoffItem findById(UUID id);

    List<HoffItem> findAll();
    List<HoffItem> findByArticleOrderByDateUpdateDesc(String article);
}
