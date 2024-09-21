package com.pdrw.pdrw.pinskdrev.service.impl;

import com.pdrw.pdrw.pinskdrev.model.Pinskdrev;
import com.pdrw.pdrw.pinskdrev.model.wrappers.PinskdrevAverageCategoryData;
import com.pdrw.pdrw.pinskdrev.model.wrappers.PinskdrevData;
import com.pdrw.pdrw.pinskdrev.repository.PinskdrevRepository;
import com.pdrw.pdrw.pinskdrev.service.PinskdrevService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

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

    @Override
    public List<PinskdrevAverageCategoryData> getPinskdrevAverageCategoryData() {
        List<PinskdrevAverageCategoryData> result = new ArrayList<>();
        Map<String, BigDecimal> averageByType = getAverageByType();
        Set<String> types = averageByType.keySet();
        for (String type : types) {
            PinskdrevAverageCategoryData data = new PinskdrevAverageCategoryData()
                    .setNameCategory(type)
                    .setAveragePrice(averageByType.get(type));

            Optional<Pinskdrev> pinskdrevMinOptional = pinskdrevRepository.findByTypeAndMinPrice(type);
            pinskdrevMinOptional.ifPresent(data::setMinPrice);
            Optional<Pinskdrev> pinskdrevMaxOptional = pinskdrevRepository.findByTypeAndMaxPrice(type);
            pinskdrevMaxOptional.ifPresent(data::setMaxPrice);
            result.add(data);
        }
        return result;
    }

    @Override
    public List<Pinskdrev> getNewCreatedItems(Integer limit) {
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.withHour(0).withMinute(0);
        Date fromDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date toDay = new Date();
        return pinskdrevRepository.findNewCreatedItems(fromDay, toDay, limit);
    }

    @Override
    public List<Pinskdrev> getNotUpdatedItems(Integer limit) {
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.withHour(0).withMinute(0);
        Date fromDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return pinskdrevRepository.findNotUpdatedItems(fromDay, limit);
    }

    @Override
    public List<Pinskdrev> getChangedItems(Integer limit) {
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.withHour(0).withMinute(0);
        Date fromDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date toDay = new Date();
        return pinskdrevRepository.getChangedItems(fromDay, toDay, limit);
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
