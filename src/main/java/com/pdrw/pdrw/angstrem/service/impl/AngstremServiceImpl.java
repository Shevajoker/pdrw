package com.pdrw.pdrw.angstrem.service.impl;

import com.pdrw.pdrw.angstrem.model.Angstrem;
import com.pdrw.pdrw.angstrem.model.wrappers.AngstremAverageCategoryData;
import com.pdrw.pdrw.angstrem.repository.AngstremRepository;
import com.pdrw.pdrw.angstrem.service.AngstremService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class AngstremServiceImpl implements AngstremService {

    private final AngstremRepository angstremRepository;

    public AngstremServiceImpl(AngstremRepository angstremRepository) {
        this.angstremRepository = angstremRepository;
    }

    @Override
    @Transactional
    public Angstrem create(Angstrem item) {
        angstremRepository.save(item);
        return angstremRepository.findById(item.getId()).orElseThrow();
    }

    @Override
    @Transactional
    public Angstrem findById(UUID id) {
        return angstremRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public List<Angstrem> findAll() {
        return angstremRepository.findAll();
    }

    @Override
    @Transactional
    public List<Angstrem> findByArticleOrderByDateUpdateDesc(String article) {
        return angstremRepository.findByArticleOrderByDateUpdateDesc(article);
    }

    @Override
    @Transactional
    public List<String> findAllTypes() {
        return angstremRepository.findAllTypes();
    }

    @Override
    @Transactional
    public List<Angstrem> findAllByType(String type) {
        return angstremRepository.findByTypeLike(type);
    }

//    @Override
//    @Transactional
//    public AngstremData getData() {
//        AngstremData angstremData = new AngstremData();
//        angstremData.setAveragePrice(getAveragePrice());
//        angstremData.setCountAll(getCountAll());
//        angstremData.setAverageByType(getAverageByType());
//        angstremData.setArticleChangeData(getAtricleCahngeData());
//
//        return angstremData;
//    }

    @Override
    public List<AngstremAverageCategoryData> getAngstremAverageCategoryData() {
        List<AngstremAverageCategoryData> result = new ArrayList<>();
        Map<String, BigDecimal> averageByType = getAverageByType();
        Set<String> types = averageByType.keySet();
        for (String type : types) {
            AngstremAverageCategoryData data = new AngstremAverageCategoryData()
                    .setNameCategory(type)
                    .setAveragePrice(averageByType.get(type));

            Optional<Angstrem> angstremMinOptional = angstremRepository.findByTypeAndMinPrice(type);
            angstremMinOptional.ifPresent(data::setMinPrice);
            Optional<Angstrem> angstremMaxOptional = angstremRepository.findByTypeAndMaxPrice(type);
            angstremMaxOptional.ifPresent(data::setMaxPrice);
            result.add(data);
        }
        return result;
    }

    @Override
    public List<Angstrem> getNewCreatedItems(Integer limit) {
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.withHour(0).withMinute(0);
        Date fromDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date toDay = new Date();
        return angstremRepository.findNewCreatedItems(fromDay, toDay, limit);
    }

    @Override
    public List<Angstrem> getNotUpdatedItems(Integer limit) {
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.withHour(0).withMinute(0);
        Date fromDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return angstremRepository.findNotUpdatedItems(fromDay, limit);
    }

    @Override
    public Map<String, List<Angstrem>> getChangedItems(Integer limit) {
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.withHour(0).withMinute(0);
        Date fromDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date toDay = new Date();
        Map<String, List<Angstrem>> result = new HashMap<>();
        List<Angstrem> angstremList = angstremRepository.getChangedItems(fromDay, toDay, limit);
        for (Angstrem angstrem : angstremList) {
            result.put(angstrem.getArticle(), angstremRepository.getItemWithPrevious(angstrem.getArticle()));
        }
        return result;
    }

    private Map<String, List<Angstrem>> getAtricleCahngeData() {
        Map<String, List<Angstrem>> map = new HashMap<>();
        List<String> atricles = angstremRepository.findAllArticles();
        for (String article : atricles) {
            List<Angstrem> angstremList = angstremRepository.findByArticleOrderByDateUpdateDesc(article);
            map.put(article, angstremList);
        }
        return map;
    }

    private Map<String, BigDecimal> getAverageByType() {
        Map<String, BigDecimal> map = new HashMap<>();
        List<String> types = angstremRepository.findAllTypes();
        for (String type : types) {
            BigDecimal averagePrice = angstremRepository.getAveragePriceByType(type);
            map.put(type, averagePrice);
        }
        return map;
    }

    private Long getCountAll() {
        return angstremRepository.countAngstremByActualTrue();
    }

    private BigDecimal getAveragePrice() {
        return angstremRepository.getAveragePrice();
    }
}
