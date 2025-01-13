package com.pdrw.pdrw.triya.service;

import com.pdrw.pdrw.triya.model.TriyaRu;
import com.pdrw.pdrw.triya.model.wrappers.TriyaRuAverageCategoryData;
import com.pdrw.pdrw.triya.model.wrappers.TriyaRuData;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface TriyaRuService {
    TriyaRu create(TriyaRu item);

    TriyaRu findById(UUID id);

    List<TriyaRu> findAll();

    List<TriyaRu> findByArticleOrderByDateUpdateDesc(String article);

    List<String> findAllTypes();

    List<TriyaRu> findAllByType(String type);

    TriyaRuData getData();

    List<TriyaRuAverageCategoryData> getTriyaRuAverageCategoryData();

    List<TriyaRu> getNewCreatedItems(Integer limit);

    List<TriyaRu> getNotUpdatedItems(Integer limit);

    Map<String, List<TriyaRu>> getChangedItems(Integer limit);

    List<TriyaRu> findActualByType(String type);

    List<TriyaRu> findActualWithSaleByType(String type);

    Integer countItemsByType(String type);
}
