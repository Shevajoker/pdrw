package com.pdrw.pdrw.bestmebelru.service;

import com.pdrw.pdrw.bestmebelru.model.BestmebelRu;
import com.pdrw.pdrw.bestmebelru.model.wrappers.BestmebelRuAverageCategoryData;
import com.pdrw.pdrw.bestmebelru.model.wrappers.BestmebelRuData;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface BestmebelRuService {
    BestmebelRu create(BestmebelRu item);

    BestmebelRu findById(UUID id);

    List<BestmebelRu> findAll();

    List<BestmebelRu> findByArticleOrderByDateUpdateDesc(String article);

    List<String> findAllTypes();

    List<BestmebelRu> findAllByType(String type);

    BestmebelRuData getData();

    List<BestmebelRuAverageCategoryData> getBestmebelRuAverageCategoryData();

    List<BestmebelRu> getNewCreatedItems(Integer limit);

    List<BestmebelRu> getNotUpdatedItems(Integer limit);

    Map<String, List<BestmebelRu>> getChangedItems(Integer limit);

    List<BestmebelRu> findActualByType(String type);

    List<BestmebelRu> findActualWithSaleByType(String type);

    Integer countItemsByType(String type);
}
