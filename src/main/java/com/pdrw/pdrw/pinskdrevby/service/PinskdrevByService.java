package com.pdrw.pdrw.pinskdrevby.service;

import com.pdrw.pdrw.pinskdrevby.model.PinskdrevBy;
import com.pdrw.pdrw.pinskdrevby.model.wrappers.PinskdrevByAverageCategoryData;
import com.pdrw.pdrw.pinskdrevby.model.wrappers.PinskdrevByData;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface PinskdrevByService {
    PinskdrevBy create(PinskdrevBy item);

    PinskdrevBy findById(UUID id);

    List<PinskdrevBy> findAll();

    List<PinskdrevBy> findByArticleOrderByDateUpdateDesc(String article);

    List<String> findAllTypes();

    List<PinskdrevBy> findAllByType(String type);

    PinskdrevByData getData();

    List<PinskdrevByAverageCategoryData> getPinskdrevByAverageCategoryData();

    List<PinskdrevBy> getNewCreatedItems(Integer limit);

    List<PinskdrevBy> getNotUpdatedItems(Integer limit);

    Map<String, List<PinskdrevBy>> getChangedItems(Integer limit);

    List<PinskdrevBy> findActualByType(String type);

    List<PinskdrevBy> findActualWithSaleByType(String type);

    Integer countItemsByType(String type);
}
