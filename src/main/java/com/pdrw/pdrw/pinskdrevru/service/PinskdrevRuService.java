package com.pdrw.pdrw.pinskdrevru.service;

import com.pdrw.pdrw.pinskdrevru.model.PinskdrevRu;
import com.pdrw.pdrw.pinskdrevru.model.wrappers.PinskdrevRuAverageCategoryData;
import com.pdrw.pdrw.pinskdrevru.model.wrappers.PinskdrevRuData;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface PinskdrevRuService {
    PinskdrevRu create(PinskdrevRu item);

    PinskdrevRu findById(UUID id);

    List<PinskdrevRu> findAll();

    List<PinskdrevRu> findByArticleOrderByDateUpdateDesc(String article);

    List<String> findAllTypes();

    List<PinskdrevRu> findAllByType(String type);

    PinskdrevRuData getData();

    List<PinskdrevRuAverageCategoryData> getPinskdrevRuAverageCategoryData();

    List<PinskdrevRu> getNewCreatedItems(Integer limit);

    List<PinskdrevRu> getNotUpdatedItems(Integer limit);

    Map<String, List<PinskdrevRu>> getChangedItems(Integer limit);

    List<PinskdrevRu> findActualByType(String type);
}
