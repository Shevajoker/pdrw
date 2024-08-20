package com.pdrw.pdrw.pinskdrev.service;

import com.pdrw.pdrw.pinskdrev.model.Pinskdrev;
import com.pdrw.pdrw.pinskdrev.model.wrappers.PinskdrevData;

import java.util.List;
import java.util.UUID;

public interface PinskdrevService {
    Pinskdrev create(Pinskdrev item);

    Pinskdrev findById(UUID id);

    List<Pinskdrev> findAll();

    List<Pinskdrev> findByArticleOrderByDateUpdateDesc(String article);

    List<String> findAllTypes();

    List<Pinskdrev> findAllByType(String type);

    PinskdrevData getData();
}
