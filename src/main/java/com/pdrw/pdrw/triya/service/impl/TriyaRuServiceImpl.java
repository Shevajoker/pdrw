package com.pdrw.pdrw.triya.service.impl;

import com.pdrw.pdrw.triya.model.TriyaRu;
import com.pdrw.pdrw.triya.model.wrappers.TriyaRuAverageCategoryData;
import com.pdrw.pdrw.triya.model.wrappers.TriyaRuData;
import com.pdrw.pdrw.triya.repository.TriyaRuRepository;
import com.pdrw.pdrw.triya.service.TriyaRuService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class TriyaRuServiceImpl implements TriyaRuService {

    private final TriyaRuRepository triyaRuRepository;

    public TriyaRuServiceImpl(TriyaRuRepository triyaRuRepository) {
        this.triyaRuRepository = triyaRuRepository;
    }

    @Override
    @Transactional
    public TriyaRu create(TriyaRu item) {
        triyaRuRepository.save(item);
        return triyaRuRepository.findById(item.getId()).orElseThrow();
    }

    @Override
    @Transactional
    public TriyaRu findById(UUID id) {
        return triyaRuRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public List<TriyaRu> findAll() {
        return triyaRuRepository.findAll();
    }

    @Override
    @Transactional
    public List<TriyaRu> findByArticleOrderByDateUpdateDesc(String article) {
        return triyaRuRepository.findByArticleOrderByDateUpdateDesc(article);
    }

    @Override
    @Transactional
    public List<String> findAllTypes() {
        return triyaRuRepository.findAllTypes();
    }

    @Override
    @Transactional
    public List<TriyaRu> findAllByType(String type) {
        return triyaRuRepository.findByTypeLike(type);
    }

    @Override
    @Transactional
    public TriyaRuData getData() {
        TriyaRuData triyaRuData = new TriyaRuData();
        triyaRuData.setAveragePrice(getAveragePrice());
        triyaRuData.setCountAll(getCountAll());
        triyaRuData.setAverageByType(getAverageByType());
        triyaRuData.setArticleChangeData(getAtricleCahngeData());

        return triyaRuData;
    }

    @Override
    public List<TriyaRuAverageCategoryData> getTriyaRuAverageCategoryData() {
        List<TriyaRuAverageCategoryData> result = new ArrayList<>();
        Map<String, BigDecimal> averageByType = getAverageByType();
        Set<String> types = averageByType.keySet();
        for (String type : types) {
            TriyaRuAverageCategoryData data = new TriyaRuAverageCategoryData()
                    .setNameCategory(type)
                    .setAveragePrice(averageByType.get(type));

            Optional<TriyaRu> triyaRuMinOptional = triyaRuRepository.findByTypeAndMinPrice(type);
            triyaRuMinOptional.ifPresent(data::setMinPrice);
            Optional<TriyaRu> triyaRuMaxOptional = triyaRuRepository.findByTypeAndMaxPrice(type);
            triyaRuMaxOptional.ifPresent(data::setMaxPrice);
            result.add(data);
        }
        return result;
    }

    @Override
    public List<TriyaRu> getNewCreatedItems(Integer limit) {
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.withHour(0).withMinute(0);
        Date fromDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date toDay = new Date();
        return triyaRuRepository.findNewCreatedItems(fromDay, toDay, limit);
    }

    @Override
    public List<TriyaRu> getNotUpdatedItems(Integer limit) {
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.withHour(0).withMinute(0);
        Date fromDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return triyaRuRepository.findNotUpdatedItems(fromDay, limit);
    }

    @Override
    public Map<String, List<TriyaRu>> getChangedItems(Integer limit) {
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.withHour(0).withMinute(0);
        Date fromDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date toDay = new Date();
        Map<String, List<TriyaRu>> result = new HashMap<>();
        List<TriyaRu> triyaRuList = triyaRuRepository.getChangedItems(fromDay, toDay, limit);
        for (TriyaRu triyaRu : triyaRuList) {
            result.put(triyaRu.getArticle(), triyaRuRepository.getItemWithPrevious(triyaRu.getArticle()));
        }
        return result;
    }

    @Override
    @Transactional
    public List<TriyaRu> findActualByType(String type) {
        return triyaRuRepository.findActualByType(type);
    }

    @Override
    public List<TriyaRu> findActualWithSaleByType(String type) {
        return triyaRuRepository.findActualWithSaleByType(type);
    }

    @Override
    public Integer countItemsByType(String type) {
        return triyaRuRepository.countItemsByType(type);
    }

    private Map<String, List<TriyaRu>> getAtricleCahngeData() {
        Map<String, List<TriyaRu>> map = new HashMap<>();
        List<String> atricles = triyaRuRepository.findAllArticles();
        for (String article : atricles) {
            List<TriyaRu> triyaRuList = triyaRuRepository.findByArticleOrderByDateUpdateDesc(article);
            map.put(article, triyaRuList);
        }
        return map;
    }

    private Map<String, BigDecimal> getAverageByType() {
        Map<String, BigDecimal> map = new HashMap<>();
        List<String> types = triyaRuRepository.findAllTypes();
        for (String type : types) {
            BigDecimal averagePrice = triyaRuRepository.getAveragePriceByType(type);
            map.put(type, averagePrice);
        }
        return map;
    }

    private Long getCountAll() {
        return triyaRuRepository.countByActualTrue();
    }

    private BigDecimal getAveragePrice() {
        return triyaRuRepository.getAveragePrice();
    }
}
