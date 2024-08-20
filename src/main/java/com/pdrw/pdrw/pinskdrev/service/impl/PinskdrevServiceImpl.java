package com.pdrw.pdrw.pinskdrev.service.impl;

import com.pdrw.pdrw.pinskdrev.model.Pinskdrev;
import com.pdrw.pdrw.pinskdrev.model.wrappers.PinskdrevData;
import com.pdrw.pdrw.pinskdrev.repository.PinskdrevRepository;
import com.pdrw.pdrw.pinskdrev.service.PinskdrevService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PinskdrevServiceImpl implements PinskdrevService {

    private final PinskdrevRepository pinskdrevRepository;

    public PinskdrevServiceImpl(PinskdrevRepository pinskdrevRepository) {
        this.pinskdrevRepository = pinskdrevRepository;
    }

    @Override
    @Transactional
    public Pinskdrev create(Pinskdrev item) {
        pinskdrevRepository.save(item);
        return pinskdrevRepository.findById(item.getId()).orElseThrow();
    }

    @Override
    @Transactional
    public Pinskdrev findById(UUID id) {
        return pinskdrevRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public List<Pinskdrev> findAll() {
        return pinskdrevRepository.findAll();
    }

    @Override
    @Transactional
    public List<Pinskdrev> findByArticleOrderByDateUpdateDesc(String article) {
        return pinskdrevRepository.findByArticleOrderByDateUpdateDesc(article);
    }

    @Override
    @Transactional
    public List<String> findAllTypes() {
        return pinskdrevRepository.findAllTypes();
    }

    @Override
    @Transactional
    public List<Pinskdrev> findAllByType(String type) {
        return pinskdrevRepository.findByTypeLike(type);
    }

    @Override
    @Transactional
    public PinskdrevData getData() {
        PinskdrevData pinskdrevData = new PinskdrevData();
        pinskdrevData.setAveragePrice(getAveragePrice());
        pinskdrevData.setCountAll(getCountAll());
        pinskdrevData.setAverageByType(getAverageByType());
        pinskdrevData.setArticleChangeData(getAtricleCahngeData());

        return pinskdrevData;
    }

    private Map<String, List<Pinskdrev>> getAtricleCahngeData() {
        Map<String, List<Pinskdrev>> map = new HashMap<>();
        List<String> atricles = pinskdrevRepository.findAllArticles();
        for (String article : atricles) {
            List<Pinskdrev> pinskdrevList = pinskdrevRepository.findByArticleOrderByDateUpdateDesc(article);
            map.put(article, pinskdrevList);
        }
        return map;
    }

    private Map<String, BigDecimal> getAverageByType() {
        Map<String, BigDecimal> map = new HashMap<>();
        List<String> types = pinskdrevRepository.findAllTypes();
        for (String type : types) {
            BigDecimal averagePrice = pinskdrevRepository.getAveragePriceByType(type);
            map.put(type, averagePrice);
        }
        return map;
    }

    private Long getCountAll() {
        return pinskdrevRepository.countPinskdrevByActualTrue();
    }

    private BigDecimal getAveragePrice() {
        return pinskdrevRepository.getAveragePrice();
    }
}
