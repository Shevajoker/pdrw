package com.pdrw.pdrw.pinskdrevRuru.service.impl;

import com.pdrw.pdrw.pinskdrevru.model.PinskdrevRu;
import com.pdrw.pdrw.pinskdrevru.model.wrappers.PinskdrevRuAverageCategoryData;
import com.pdrw.pdrw.pinskdrevru.model.wrappers.PinskdrevRuData;
import com.pdrw.pdrw.pinskdrevru.repository.PinskdrevRuRepository;
import com.pdrw.pdrw.pinskdrevru.service.PinskdrevRuService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class PinskdrevRuServiceImpl implements PinskdrevRuService {

    private final PinskdrevRuRepository pinskdrevRuRepository;

    public PinskdrevRuServiceImpl(PinskdrevRuRepository pinskdrevRuRepository) {
        this.pinskdrevRuRepository = pinskdrevRuRepository;
    }

    @Override
    @Transactional
    public PinskdrevRu create(PinskdrevRu item) {
        pinskdrevRuRepository.save(item);
        return pinskdrevRuRepository.findById(item.getId()).orElseThrow();
    }

    @Override
    @Transactional
    public PinskdrevRu findById(UUID id) {
        return pinskdrevRuRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public List<PinskdrevRu> findAll() {
        return pinskdrevRuRepository.findAll();
    }

    @Override
    @Transactional
    public List<PinskdrevRu> findByArticleOrderByDateUpdateDesc(String article) {
        return pinskdrevRuRepository.findByArticleOrderByDateUpdateDesc(article);
    }

    @Override
    @Transactional
    public List<String> findAllTypes() {
        return pinskdrevRuRepository.findAllTypes();
    }

    @Override
    @Transactional
    public List<PinskdrevRu> findAllByType(String type) {
        return pinskdrevRuRepository.findByTypeLike(type);
    }

    @Override
    @Transactional
    public PinskdrevRuData getData() {
        PinskdrevRuData pinskdrevRuData = new PinskdrevRuData();
        pinskdrevRuData.setAveragePrice(getAveragePrice());
        pinskdrevRuData.setCountAll(getCountAll());
        pinskdrevRuData.setAverageByType(getAverageByType());
        pinskdrevRuData.setArticleChangeData(getAtricleCahngeData());

        return pinskdrevRuData;
    }

    @Override
    public List<PinskdrevRuAverageCategoryData> getPinskdrevRuAverageCategoryData() {
        List<PinskdrevRuAverageCategoryData> result = new ArrayList<>();
        Map<String, BigDecimal> averageByType = getAverageByType();
        Set<String> types = averageByType.keySet();
        for (String type : types) {
            PinskdrevRuAverageCategoryData data = new PinskdrevRuAverageCategoryData()
                    .setNameCategory(type)
                    .setAveragePrice(averageByType.get(type));

            Optional<PinskdrevRu> pinskdrevRuMinOptional = pinskdrevRuRepository.findByTypeAndMinPrice(type);
            pinskdrevRuMinOptional.ifPresent(data::setMinPrice);
            Optional<PinskdrevRu> pinskdrevRuMaxOptional = pinskdrevRuRepository.findByTypeAndMaxPrice(type);
            pinskdrevRuMaxOptional.ifPresent(data::setMaxPrice);
            result.add(data);
        }
        return result;
    }

    @Override
    public List<PinskdrevRu> getNewCreatedItems(Integer limit) {
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.withHour(0).withMinute(0);
        Date fromDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date toDay = new Date();
        return pinskdrevRuRepository.findNewCreatedItems(fromDay, toDay, limit);
    }

    @Override
    public List<PinskdrevRu> getNotUpdatedItems(Integer limit) {
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.withHour(0).withMinute(0);
        Date fromDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return pinskdrevRuRepository.findNotUpdatedItems(fromDay, limit);
    }

    @Override
    public Map<String, List<PinskdrevRu>> getChangedItems(Integer limit) {
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.withHour(0).withMinute(0);
        Date fromDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date toDay = new Date();
        Map<String, List<PinskdrevRu>> result = new HashMap<>();
        List<PinskdrevRu> pinskdrevRuList = pinskdrevRuRepository.getChangedItems(fromDay, toDay, limit);
        for (PinskdrevRu pinskdrevRu : pinskdrevRuList) {
            result.put(pinskdrevRu.getArticle(), pinskdrevRuRepository.getItemWithPrevious(pinskdrevRu.getArticle()));
        }
        return result;
    }

    @Override
    @Transactional
    public List<PinskdrevRu> findActualByType(String type) {
        return pinskdrevRuRepository.findActualByType(type);
    }

    private Map<String, List<PinskdrevRu>> getAtricleCahngeData() {
        Map<String, List<PinskdrevRu>> map = new HashMap<>();
        List<String> atricles = pinskdrevRuRepository.findAllArticles();
        for (String article : atricles) {
            List<PinskdrevRu> pinskdrevRuList = pinskdrevRuRepository.findByArticleOrderByDateUpdateDesc(article);
            map.put(article, pinskdrevRuList);
        }
        return map;
    }

    private Map<String, BigDecimal> getAverageByType() {
        Map<String, BigDecimal> map = new HashMap<>();
        List<String> types = pinskdrevRuRepository.findAllTypes();
        for (String type : types) {
            BigDecimal averagePrice = pinskdrevRuRepository.getAveragePriceByType(type);
            map.put(type, averagePrice);
        }
        return map;
    }

    private Long getCountAll() {
        return pinskdrevRuRepository.countPinskdrevRuByActualTrue();
    }

    private BigDecimal getAveragePrice() {
        return pinskdrevRuRepository.getAveragePrice();
    }
}
