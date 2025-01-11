package com.pdrw.pdrw.pinskdrevby.service.impl;

import com.pdrw.pdrw.pinskdrevby.model.PinskdrevBy;
import com.pdrw.pdrw.pinskdrevby.model.wrappers.PinskdrevByAverageCategoryData;
import com.pdrw.pdrw.pinskdrevby.model.wrappers.PinskdrevByData;
import com.pdrw.pdrw.pinskdrevby.repository.PinskdrevByRepository;
import com.pdrw.pdrw.pinskdrevby.service.PinskdrevByService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class PinskdrevByServiceImpl implements PinskdrevByService {

    private final PinskdrevByRepository pinskdrevByRepository;

    public PinskdrevByServiceImpl(PinskdrevByRepository pinskdrevByRepository) {
        this.pinskdrevByRepository = pinskdrevByRepository;
    }

    @Override
    @Transactional
    public PinskdrevBy create(PinskdrevBy item) {
        pinskdrevByRepository.save(item);
        return pinskdrevByRepository.findById(item.getId()).orElseThrow();
    }

    @Override
    @Transactional
    public PinskdrevBy findById(UUID id) {
        return pinskdrevByRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public List<PinskdrevBy> findAll() {
        return pinskdrevByRepository.findAll();
    }

    @Override
    @Transactional
    public List<PinskdrevBy> findByArticleOrderByDateUpdateDesc(String article) {
        return pinskdrevByRepository.findByArticleOrderByDateUpdateDesc(article);
    }

    @Override
    @Transactional
    public List<String> findAllTypes() {
        return pinskdrevByRepository.findAllTypes();
    }

    @Override
    @Transactional
    public List<PinskdrevBy> findAllByType(String type) {
        return pinskdrevByRepository.findByTypeLike(type);
    }

    @Override
    @Transactional
    public PinskdrevByData getData() {
        PinskdrevByData pinskdrevByData = new PinskdrevByData();
        pinskdrevByData.setAveragePrice(getAveragePrice());
        pinskdrevByData.setCountAll(getCountAll());
        pinskdrevByData.setAverageByType(getAverageByType());
        pinskdrevByData.setArticleChangeData(getAtricleCahngeData());

        return pinskdrevByData;
    }

    @Override
    public List<PinskdrevByAverageCategoryData> getPinskdrevByAverageCategoryData() {
        List<PinskdrevByAverageCategoryData> result = new ArrayList<>();
        Map<String, BigDecimal> averageByType = getAverageByType();
        Set<String> types = averageByType.keySet();
        for (String type : types) {
            PinskdrevByAverageCategoryData data = new PinskdrevByAverageCategoryData()
                    .setNameCategory(type)
                    .setAveragePrice(averageByType.get(type));

            Optional<PinskdrevBy> pinskdrevByMinOptional = pinskdrevByRepository.findByTypeAndMinPrice(type);
            pinskdrevByMinOptional.ifPresent(data::setMinPrice);
            Optional<PinskdrevBy> pinskdrevByMaxOptional = pinskdrevByRepository.findByTypeAndMaxPrice(type);
            pinskdrevByMaxOptional.ifPresent(data::setMaxPrice);
            result.add(data);
        }
        return result;
    }

    @Override
    public List<PinskdrevBy> getNewCreatedItems(Integer limit) {
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.withHour(0).withMinute(0);
        Date fromDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date toDay = new Date();
        return pinskdrevByRepository.findNewCreatedItems(fromDay, toDay, limit);
    }

    @Override
    public List<PinskdrevBy> getNotUpdatedItems(Integer limit) {
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.withHour(0).withMinute(0);
        Date fromDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return pinskdrevByRepository.findNotUpdatedItems(fromDay, limit);
    }

    @Override
    public Map<String, List<PinskdrevBy>> getChangedItems(Integer limit) {
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.withHour(0).withMinute(0);
        Date fromDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date toDay = new Date();
        Map<String, List<PinskdrevBy>> result = new HashMap<>();
        List<PinskdrevBy> pinskdrevByList = pinskdrevByRepository.getChangedItems(fromDay, toDay, limit);
        for (PinskdrevBy pinskdrevBy : pinskdrevByList) {
            result.put(pinskdrevBy.getArticle(), pinskdrevByRepository.getItemWithPrevious(pinskdrevBy.getArticle()));
        }
        return result;
    }

    @Override
    @Transactional
    public List<PinskdrevBy> findActualByType(String type) {
        return pinskdrevByRepository.findActualByType(type);
    }

    @Override
    public List<PinskdrevBy> findActualWithSaleByType(String type) {
        return pinskdrevByRepository.findActualWithSaleByType(type);
    }

    @Override
    public Integer countItemsByType(String type) {
        return pinskdrevByRepository.countItemsByType(type);
    }

    private Map<String, List<PinskdrevBy>> getAtricleCahngeData() {
        Map<String, List<PinskdrevBy>> map = new HashMap<>();
        List<String> atricles = pinskdrevByRepository.findAllArticles();
        for (String article : atricles) {
            List<PinskdrevBy> pinskdrevByList = pinskdrevByRepository.findByArticleOrderByDateUpdateDesc(article);
            map.put(article, pinskdrevByList);
        }
        return map;
    }

    private Map<String, BigDecimal> getAverageByType() {
        Map<String, BigDecimal> map = new HashMap<>();
        List<String> types = pinskdrevByRepository.findAllTypes();
        for (String type : types) {
            BigDecimal averagePrice = pinskdrevByRepository.getAveragePriceByType(type);
            map.put(type, averagePrice);
        }
        return map;
    }

    private Long getCountAll() {
        return pinskdrevByRepository.countByActualTrue();
    }

    private BigDecimal getAveragePrice() {
        return pinskdrevByRepository.getAveragePrice();
    }
}
