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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            bestmebelRuToSave.setName(item.get("nameProduct") != null ? item.get("nameProduct").asText() : "-");
            bestmebelRuToSave.setLink(item.get("uriProduct") != null ? item.get("uriProduct").asText() : "-");
            bestmebelRuToSave.setImage(item.get("photo") != null ? item.get("photo").asText() : "-");
            bestmebelRuToSave.setPriceOld(item.get("oldPrice") != null ? BigDecimal.valueOf(item.get("oldPrice").asInt()) : BigDecimal.ZERO);
            bestmebelRuToSave.setPriceNew(item.get("price") != null ? BigDecimal.valueOf(item.get("price").asInt()) : BigDecimal.ZERO);
            bestmebelRuToSave.setDiscount(calculateDiscount(item));
            bestmebelRuToSave.setCreateDate(new Date());
            bestmebelRuToSave.setDateUpdate(new Date());
            getCharacteristics(bestmebelRuToSave, item);
            validateItem(bestmebelRuToSave, message);
            List<BestmebelRu> bestmebelRuList = bestmebelRuRepository.findByArticleOrderByDateUpdateDesc(bestmebelRuToSave.getArticle());
            if (!bestmebelRuList.isEmpty()) {
                BestmebelRu bestmebelRu = bestmebelRuList.getFirst();
                if (!Objects.equals(bestmebelRuToSave.getPriceOld(), bestmebelRu.getPriceOld())
                        && !Objects.equals(bestmebelRuToSave.getPriceNew(), bestmebelRu.getPriceNew())) {
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
        if (!message.isEmpty()) {
            message.append(System.lineSeparator());
        }
        log.info(message.toString());
    }

    private void getCharacteristics(BestmebelRu bestmebelRuToSave, JsonNode item) {
        JsonNode characteristics = item.get("properties");
        bestmebelRuToSave.setType("Should be type");
        bestmebelRuToSave.setLength(100);
        bestmebelRuToSave.setWidth(100);
        bestmebelRuToSave.setHeight(100);
        bestmebelRuToSave.setWeight(100);
        bestmebelRuToSave.setVolume(10.0);
        if (characteristics != null) {
//            bestmebelRuToSave.setType(characteristics.get("Тип") != null ? characteristics.get("Тип").asText() : "-");
//            bestmebelRuToSave.setLength(characteristics.get("Длина (мм)") != null ? convertToInteger(characteristics.get("Длина (мм)").asText()) : 0);
//            bestmebelRuToSave.setWidth(characteristics.get("Ширина (мм)") != null ? convertToInteger(characteristics.get("Ширина (мм)").asText()) : 0);
//            bestmebelRuToSave.setHeight(characteristics.get("Высота (мм)") != null ? convertToInteger(characteristics.get("Высота (мм)").asText()) : 0);
//            bestmebelRuToSave.setWeight(characteristics.get("Вес") != null ? convertToInteger(characteristics.get("Вес").asText()) : 0);
//            bestmebelRuToSave.setVolume(characteristics.get("Объем") != null ? convertVolumeToInteger(characteristics.get("Объем").asText()) : 0);
        }
    }

    private Double convertVolumeToInteger(String text) {
        Pattern pattern = Pattern.compile("[-+]?\\d+(,\\d*)?");
        Matcher matcher = pattern.matcher(text);
        String trimmedText = "0";
        if (matcher.find()) {
            trimmedText = matcher.group();
        }
        trimmedText = trimmedText.replace(',', '.');
        return Double.parseDouble(trimmedText);
    }

    private Integer convertToInteger(String text) {
        Pattern pattern = Pattern.compile("\\b[\\d]+\\b");
        Matcher matcher = pattern.matcher(text);
        String trimmedText = "0";
        if (matcher.find()) {
            trimmedText = matcher.group();
        }
        return Integer.parseInt(trimmedText);
    }

    private BigDecimal calculateDiscount(JsonNode item) {

        BigDecimal priceOld = item.get("oldPrice") != null ? BigDecimal.valueOf(item.get("oldPrice").asInt()) : BigDecimal.ZERO;
        BigDecimal priceNew = item.get("price") != null ? BigDecimal.valueOf(item.get("price").asInt()) : BigDecimal.ZERO;

        if (priceOld.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return priceOld.subtract(priceNew).divide(priceOld, 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
    }
}
