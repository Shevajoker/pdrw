package com.pdrw.pdrw.bestmebelru.service.impl;

import com.pdrw.pdrw.bestmebelru.model.BestmebelRu;
import com.pdrw.pdrw.bestmebelru.model.wrappers.BestmebelRuAverageCategoryData;
import com.pdrw.pdrw.bestmebelru.model.wrappers.BestmebelRuData;
import com.pdrw.pdrw.bestmebelru.service.BestmebelRuService;
import com.pdrw.pdrw.bestmebelru.repository.BestmebelRuRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class BestmebelRuServiceImpl implements BestmebelRuService {

    private final BestmebelRuRepository bestmebelRuRepository;

    public BestmebelRuServiceImpl(BestmebelRuRepository bestmebelRuRepository) {
        this.bestmebelRuRepository = bestmebelRuRepository;
    }

    @Override
    @Transactional
    public BestmebelRu create(BestmebelRu item) {
        bestmebelRuRepository.save(item);
        return bestmebelRuRepository.findById(item.getId()).orElseThrow();
    }

    @Override
    @Transactional
    public BestmebelRu findById(UUID id) {
        return bestmebelRuRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public List<BestmebelRu> findAll() {
        return bestmebelRuRepository.findAll();
    }

    @Override
    @Transactional
    public List<BestmebelRu> findByArticleOrderByDateUpdateDesc(String article) {
        return bestmebelRuRepository.findByArticleOrderByDateUpdateDesc(article);
    }

    @Override
    @Transactional
    public List<String> findAllTypes() {
        return bestmebelRuRepository.findAllTypes();
    }

    @Override
    @Transactional
    public List<BestmebelRu> findAllByType(String type) {
        return bestmebelRuRepository.findByTypeLike(type);
    }

    @Override
    @Transactional
    public BestmebelRuData getData() {
        BestmebelRuData bestmebelRuData = new BestmebelRuData();
        bestmebelRuData.setAveragePrice(getAveragePrice());
        bestmebelRuData.setCountAll(getCountAll());
        bestmebelRuData.setAverageByType(getAverageByType());
        bestmebelRuData.setArticleChangeData(getAtricleCahngeData());

        return bestmebelRuData;
    }

    @Override
    public List<BestmebelRuAverageCategoryData> getBestmebelRuAverageCategoryData() {
        List<BestmebelRuAverageCategoryData> result = new ArrayList<>();
        Map<String, BigDecimal> averageByType = getAverageByType();
        Set<String> types = averageByType.keySet();
        for (String type : types) {
            BestmebelRuAverageCategoryData data = new BestmebelRuAverageCategoryData()
                    .setNameCategory(type)
                    .setAveragePrice(averageByType.get(type));

            Optional<BestmebelRu> bestmebelRuMinOptional = bestmebelRuRepository.findByTypeAndMinPrice(type);
            bestmebelRuMinOptional.ifPresent(data::setMinPrice);
            Optional<BestmebelRu> bestmebelRuMaxOptional = bestmebelRuRepository.findByTypeAndMaxPrice(type);
            bestmebelRuMaxOptional.ifPresent(data::setMaxPrice);
            result.add(data);
        }
        return result;
    }

    @Override
    public List<BestmebelRu> getNewCreatedItems(Integer limit) {
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.withHour(0).withMinute(0);
        Date fromDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date toDay = new Date();
        return bestmebelRuRepository.findNewCreatedItems(fromDay, toDay, limit);
    }

    @Override
    public List<BestmebelRu> getNotUpdatedItems(Integer limit) {
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.withHour(0).withMinute(0);
        Date fromDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return bestmebelRuRepository.findNotUpdatedItems(fromDay, limit);
    }

    @Override
    public Map<String, List<BestmebelRu>> getChangedItems(Integer limit) {
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.withHour(0).withMinute(0);
        Date fromDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date toDay = new Date();
        Map<String, List<BestmebelRu>> result = new HashMap<>();
        List<BestmebelRu> bestmebelRuList = bestmebelRuRepository.getChangedItems(fromDay, toDay, limit);
        for (BestmebelRu bestmebelRu : bestmebelRuList) {
            result.put(bestmebelRu.getArticle(), bestmebelRuRepository.getItemWithPrevious(bestmebelRu.getArticle()));
        }
        return result;
    }

    @Override
    @Transactional
    public List<BestmebelRu> findActualByType(String type) {
        return bestmebelRuRepository.findActualByType(type);
    }

    @Override
    public List<BestmebelRu> findActualWithSaleByType(String type) {
        return bestmebelRuRepository.findActualWithSaleByType(type);
    }

    @Override
    public Integer countItemsByType(String type) {
        return bestmebelRuRepository.countItemsByType(type);
    }

    private Map<String, List<BestmebelRu>> getAtricleCahngeData() {
        Map<String, List<BestmebelRu>> map = new HashMap<>();
        List<String> atricles = bestmebelRuRepository.findAllArticles();
        for (String article : atricles) {
            List<BestmebelRu> bestmebelRuList = bestmebelRuRepository.findByArticleOrderByDateUpdateDesc(article);
            map.put(article, bestmebelRuList);
        }
        return map;
    }

    private Map<String, BigDecimal> getAverageByType() {
        Map<String, BigDecimal> map = new HashMap<>();
        List<String> types = bestmebelRuRepository.findAllTypes();
        for (String type : types) {
            BigDecimal averagePrice = bestmebelRuRepository.getAveragePriceByType(type);
            map.put(type, averagePrice);
        }
        return map;
    }

    private Long getCountAll() {
        return bestmebelRuRepository.countBestmebelRuByActualTrue();
    }

    private BigDecimal getAveragePrice() {
        return bestmebelRuRepository.getAveragePrice();
    }
}
