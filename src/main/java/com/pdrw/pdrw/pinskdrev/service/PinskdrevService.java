package com.pdrw.pdrw.pinskdrev.service;

import com.pdrw.pdrw.pinskdrev.model.Pinskdrev;
import com.pdrw.pdrw.pinskdrev.model.wrappers.PinskdrevAverageCategoryData;
import com.pdrw.pdrw.pinskdrev.model.wrappers.PinskdrevData;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface PinskdrevService {
    Pinskdrev create(Pinskdrev item);

    Pinskdrev findById(UUID id);

    List<Pinskdrev> findAll();

    List<Pinskdrev> findByArticleOrderByDateUpdateDesc(String article);

    List<String> findAllTypes();

    List<Pinskdrev> findAllByType(String type);

    PinskdrevData getData();

    List<PinskdrevAverageCategoryData> getPinskdrevAverageCategoryData();

    List<Pinskdrev> getNewCreatedItems(Integer limit);

    List<Pinskdrev> getNotUpdatedItems(Integer limit);

    Map<String, List<Pinskdrev>> getChangedItems(Integer limit);
}
