package com.pdrw.pdrw.triya.service.impl;

import com.pdrw.pdrw.triya.model.Triya;
import com.pdrw.pdrw.triya.model.wrappers.TriyaAverageCategoryData;
import com.pdrw.pdrw.triya.repository.TriyaRepository;
import com.pdrw.pdrw.triya.service.TriyaService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class TriyaServiceImpl implements TriyaService {

    private final TriyaRepository triyaRepository;

    public TriyaServiceImpl(TriyaRepository triyaRepository) {
        this.triyaRepository = triyaRepository;
    }

    @Override
    @Transactional
    public Triya create(Triya item) {
        triyaRepository.save(item);
        return triyaRepository.findById(item.getId()).orElseThrow();
    }

    @Override
    @Transactional
    public Triya findById(UUID id) {
        return triyaRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public List<Triya> findAll() {
        return triyaRepository.findAll();
    }

    @Override
    @Transactional
    public List<Triya> findByArticleOrderByDateUpdateDesc(String article) {
        return triyaRepository.findByArticleOrderByDateUpdateDesc(article);
    }

    @Override
    @Transactional
    public List<String> findAllTypes() {
        return triyaRepository.findAllTypes();
    }

    @Override
    @Transactional
    public List<Triya> findAllByType(String type) {
        return triyaRepository.findByTypeLike(type);
    }

//    @Override
//    @Transactional
//    public TriyaData getData() {
//        TriyaData triyaData = new TriyaData();
//        triyaData.setAveragePrice(getAveragePrice());
//        triyaData.setCountAll(getCountAll());
//        triyaData.setAverageByType(getAverageByType());
//        triyaData.setArticleChangeData(getAtricleCahngeData());
//
//        return triyaData;
//    }

    @Override
    public List<TriyaAverageCategoryData> getTriyaAverageCategoryData() {
        List<TriyaAverageCategoryData> result = new ArrayList<>();
        Map<String, BigDecimal> averageByType = getAverageByType();
        Set<String> types = averageByType.keySet();
        for (String type : types) {
            TriyaAverageCategoryData data = new TriyaAverageCategoryData()
                    .setNameCategory(type)
                    .setAveragePrice(averageByType.get(type));

            Optional<Triya> triyaMinOptional = triyaRepository.findByTypeAndMinPrice(type);
            triyaMinOptional.ifPresent(data::setMinPrice);
            Optional<Triya> triyaMaxOptional = triyaRepository.findByTypeAndMaxPrice(type);
            triyaMaxOptional.ifPresent(data::setMaxPrice);
            result.add(data);
        }
        return result;
    }

    @Override
    public List<Triya> getNewCreatedItems(Integer limit) {
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.withHour(0).withMinute(0);
        Date fromDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date toDay = new Date();
        return triyaRepository.findNewCreatedItems(fromDay, toDay, limit);
    }

    @Override
    public List<Triya> getNotUpdatedItems(Integer limit) {
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.withHour(0).withMinute(0);
        Date fromDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return triyaRepository.findNotUpdatedItems(fromDay, limit);
    }

    @Override
    public Map<String, List<Triya>> getChangedItems(Integer limit) {
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.withHour(0).withMinute(0);
        Date fromDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date toDay = new Date();
        Map<String, List<Triya>> result = new HashMap<>();
        List<Triya> triyaList = triyaRepository.getChangedItems(fromDay, toDay, limit);
        for (Triya triya : triyaList) {
            result.put(triya.getArticle(), triyaRepository.getItemWithPrevious(triya.getArticle()));
        }
        return result;
    }

    private Map<String, List<Triya>> getAtricleCahngeData() {
        Map<String, List<Triya>> map = new HashMap<>();
        List<String> atricles = triyaRepository.findAllArticles();
        for (String article : atricles) {
            List<Triya> triyaList = triyaRepository.findByArticleOrderByDateUpdateDesc(article);
            map.put(article, triyaList);
        }
        return map;
    }

    private Map<String, BigDecimal> getAverageByType() {
        Map<String, BigDecimal> map = new HashMap<>();
        List<String> types = triyaRepository.findAllTypes();
        for (String type : types) {
            BigDecimal averagePrice = triyaRepository.getAveragePriceByType(type);
            map.put(type, averagePrice);
        }
        return map;
    }

    private Long getCountAll() {
        return triyaRepository.countTriyaByActualTrue();
    }

    private BigDecimal getAveragePrice() {
        return triyaRepository.getAveragePrice();
    }
}
