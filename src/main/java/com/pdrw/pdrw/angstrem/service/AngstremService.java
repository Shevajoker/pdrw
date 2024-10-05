package com.pdrw.pdrw.angstrem.service;

import com.pdrw.pdrw.angstrem.model.Angstrem;
import com.pdrw.pdrw.angstrem.model.wrappers.AngstremAverageCategoryData;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AngstremService {
    Angstrem create(Angstrem item);

    Angstrem findById(UUID id);

    List<Angstrem> findAll();

    List<Angstrem> findByArticleOrderByDateUpdateDesc(String article);

    List<String> findAllTypes();

    List<Angstrem> findAllByType(String type);

//    AngstremData getData();

    List<AngstremAverageCategoryData> getAngstremAverageCategoryData();

    List<Angstrem> getNewCreatedItems(Integer limit);

    List<Angstrem> getNotUpdatedItems(Integer limit);

    Map<String, List<Angstrem>> getChangedItems(Integer limit);
}
