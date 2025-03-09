package com.pdrw.pdrw.bestmebelru.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdrw.pdrw.bestmebelru.model.BestmebelRu;
import com.pdrw.pdrw.bestmebelru.repository.BestmebelRuRepository;
import com.pdrw.pdrw.bestmebelru.service.BestmebelRuDataService;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Log4j2
@Service
public class BestmebelRuDataServiceImpl implements BestmebelRuDataService {

    public static final String DEFAULT_STRING_VALUE = "-";
    private final ObjectMapper objectMapper;
    private final BestmebelRuRepository bestmebelRuRepository;
//    private final AlertClient alertClient;

    public BestmebelRuDataServiceImpl(ObjectMapper objectMapper,
                                      BestmebelRuRepository bestmebelRuRepository) {
        this.objectMapper = objectMapper;
        this.bestmebelRuRepository = bestmebelRuRepository;
    }


    @Override
    @Transactional
    public int setData(String data) {
        StringBuilder message = new StringBuilder();
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to load data", e);
        }
        int count = 0;
        JsonNode items = Optional.ofNullable(jsonNode).orElseThrow();
        for (JsonNode item : items) {
            BestmebelRu bestmebelRuToSave = new BestmebelRu();
            bestmebelRuToSave.setArticle(item.get("dataId") != null ? item.get("dataId").asText() : DEFAULT_STRING_VALUE);
            bestmebelRuToSave.setName(item.get("nameProduct") != null ? item.get("nameProduct").asText() : DEFAULT_STRING_VALUE);
            bestmebelRuToSave.setLink(item.get("uriProduct") != null ? item.get("uriProduct").asText() : DEFAULT_STRING_VALUE);
            bestmebelRuToSave.setImage(item.get("photo") != null ? item.get("photo").asText() : DEFAULT_STRING_VALUE);
            bestmebelRuToSave.setType(item.get("type") != null ? item.get("type").asText() : DEFAULT_STRING_VALUE);
            bestmebelRuToSave.setPriceOld(item.get("oldPrice") != null ? BigDecimal.valueOf(item.get("oldPrice").asInt()) : BigDecimal.ZERO);
            bestmebelRuToSave.setPriceNew(item.get("price") != null ? BigDecimal.valueOf(item.get("price").asInt()) : BigDecimal.ZERO);
            bestmebelRuToSave.setDiscount(calculateDiscount(item));
            bestmebelRuToSave.setCreateDate(new Date());
            bestmebelRuToSave.setDateUpdate(new Date());
            validateItem(bestmebelRuToSave, message);
            List<BestmebelRu> bestmebelRuList = bestmebelRuRepository.findByArticleOrderByDateUpdateDesc(bestmebelRuToSave.getArticle());
            if (!bestmebelRuList.isEmpty()) {
                BestmebelRu bestmebelRu = bestmebelRuList.getFirst();
                if (!Objects.equals(bestmebelRuToSave.getPriceOld(), bestmebelRu.getPriceOld())
                        || !Objects.equals(bestmebelRuToSave.getPriceNew(), bestmebelRu.getPriceNew())) {
                    bestmebelRuToSave.setActual(Boolean.TRUE);
                    bestmebelRuRepository.markActualFalse(bestmebelRuToSave.getArticle());
                    bestmebelRuRepository.save(bestmebelRuToSave);
                    count++;
                } else {
                    bestmebelRu.setDateUpdate(new Date());
                    bestmebelRuRepository.save(bestmebelRu);
                }
            } else {
                bestmebelRuToSave.setActual(Boolean.TRUE);
                bestmebelRuRepository.markActualFalse(bestmebelRuToSave.getArticle());
                bestmebelRuRepository.save(bestmebelRuToSave);
                count++;
            }
        }
        return count;
    }

    private void validateItem(BestmebelRu bestmebelRuToSave, StringBuilder message) {
        if (bestmebelRuToSave.getArticle().equals(DEFAULT_STRING_VALUE)) {
            message.append("- no dataId -");
        }
        if (bestmebelRuToSave.getName().equals(DEFAULT_STRING_VALUE)) {
            message.append("- no nameProduct -");
        }
        if (bestmebelRuToSave.getLink().equals(DEFAULT_STRING_VALUE)) {
            message.append("- no uriProduct -");
        }
        if (bestmebelRuToSave.getImage().equals(DEFAULT_STRING_VALUE)) {
            message.append("- no photo -");
        }
        if (bestmebelRuToSave.getPriceNew().compareTo(BigDecimal.ZERO) == 0) {
            message.append("- no price -");
        }
        if (bestmebelRuToSave.getType().equals(DEFAULT_STRING_VALUE)) {
            message.append("- no type -");
        }
        if (!message.isEmpty()) {
            message.append(System.lineSeparator());
        }
        log.info(message.toString());
    }

    private BigDecimal calculateDiscount(JsonNode item) {

        BigDecimal priceOld = item.get("oldPrice") != null ? BigDecimal.valueOf(item.get("oldPrice").asInt()) : BigDecimal.ZERO;
        BigDecimal priceNew = item.get("price") != null ? BigDecimal.valueOf(item.get("price").asInt()) : BigDecimal.ZERO;

        if (priceOld.compareTo(BigDecimal.ZERO) == 0 || priceOld.compareTo(priceNew) < 0) {
            return BigDecimal.ZERO;
        }
        return priceOld.subtract(priceNew).divide(priceOld, 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
    }
}
