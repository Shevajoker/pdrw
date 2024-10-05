package com.pdrw.pdrw.triya.service;

import com.pdrw.pdrw.triya.model.Triya;
import com.pdrw.pdrw.triya.model.wrappers.TriyaAverageCategoryData;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface TriyaService {
    Triya create(Triya item);

    Triya findById(UUID id);

    List<Triya> findAll();

    List<Triya> findByArticleOrderByDateUpdateDesc(String article);

    List<String> findAllTypes();

    List<Triya> findAllByType(String type);

//    TriyaData getData();

    List<TriyaAverageCategoryData> getTriyaAverageCategoryData();

    List<Triya> getNewCreatedItems(Integer limit);

    List<Triya> getNotUpdatedItems(Integer limit);

    Map<String, List<Triya>> getChangedItems(Integer limit);
}
