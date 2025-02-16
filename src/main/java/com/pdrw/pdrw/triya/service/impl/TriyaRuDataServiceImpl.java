package com.pdrw.pdrw.triya.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdrw.pdrw.triya.model.TriyaRu;
import com.pdrw.pdrw.triya.repository.TriyaRuRepository;
import com.pdrw.pdrw.triya.service.TriyaRuDataService;
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
public class TriyaRuDataServiceImpl implements TriyaRuDataService {

    public static final String DEFAULT_STRING_VALUE = "-";
    private final ObjectMapper objectMapper;
    private final TriyaRuRepository triyaRuRepository;

    public TriyaRuDataServiceImpl(ObjectMapper objectMapper,
                                  TriyaRuRepository triyaRuRepository) {
        this.objectMapper = objectMapper;
        this.triyaRuRepository = triyaRuRepository;
    }

    @Override
    @Transactional
    public int setData(String data) {
        StringBuilder message = new StringBuilder();
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(data);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RuntimeException("Failed to load data", e);
        }
        int count = 0;
        JsonNode items = Optional.ofNullable(jsonNode).orElseThrow();
        for (JsonNode item : items) {
            TriyaRu triyaRuToSave = new TriyaRu();
            triyaRuToSave.setArticle(item.get("dataId") != null ? item.get("dataId").asText() : DEFAULT_STRING_VALUE);
            triyaRuToSave.setName(item.get("nameProduct") != null ? item.get("nameProduct").asText() : "-");
            triyaRuToSave.setLink(item.get("uriProduct") != null ? item.get("uriProduct").asText() : "-");
            triyaRuToSave.setImage(item.get("photo") != null ? item.get("photo").asText() : "-");
            triyaRuToSave.setPriceOld(item.get("oldPrice") != null ? BigDecimal.valueOf(item.get("oldPrice").asInt()) : BigDecimal.ZERO);
            triyaRuToSave.setPriceNew(item.get("price") != null ? BigDecimal.valueOf(item.get("price").asInt()) : BigDecimal.ZERO);
            triyaRuToSave.setDiscount(calculateDiscount(item));
            triyaRuToSave.setCreateDate(new Date());
            triyaRuToSave.setDateUpdate(new Date());
            getCharacteristics(triyaRuToSave, item);
            validateItem(triyaRuToSave, message);
            List<TriyaRu> triyaRuList = triyaRuRepository.findByArticleOrderByDateUpdateDesc(triyaRuToSave.getArticle());
            if (!triyaRuList.isEmpty()) {
                TriyaRu triyaRu = triyaRuList.getFirst();
                if (!Objects.equals(triyaRuToSave.getPriceOld(), triyaRu.getPriceOld())
                        && !Objects.equals(triyaRuToSave.getPriceNew(), triyaRu.getPriceNew())) {
                    triyaRuToSave.setActual(Boolean.TRUE);
                    triyaRuRepository.markActualFalse(triyaRuToSave.getArticle());
                    triyaRuRepository.save(triyaRuToSave);
                    count++;
                } else {
                    triyaRu.setDateUpdate(new Date());
                    triyaRuRepository.save(triyaRu);
                }
            } else {
                triyaRuToSave.setActual(Boolean.TRUE);
                triyaRuRepository.markActualFalse(triyaRuToSave.getArticle());
                triyaRuRepository.save(triyaRuToSave);
                count++;
            }
        }
        return count;
    }

    private void validateItem(TriyaRu triyaRuToSave, StringBuilder message) {
        if (triyaRuToSave.getArticle().equals(DEFAULT_STRING_VALUE)) {
            message.append("- no dataId -");
        }
        if (triyaRuToSave.getName().equals(DEFAULT_STRING_VALUE)) {
            message.append("- no nameProduct -");
        }
        if (triyaRuToSave.getLink().equals(DEFAULT_STRING_VALUE)) {
            message.append("- no uriProduct -");
        }
        if (triyaRuToSave.getImage().equals(DEFAULT_STRING_VALUE)) {
            message.append("- no photo -");
        }
        if (triyaRuToSave.getPriceNew().compareTo(BigDecimal.ZERO) == 0) {
            message.append("- no price -");
        }
        if (!message.isEmpty()) {
            message.append(System.lineSeparator());
        }
        log.info(message.toString());
    }

    private void getCharacteristics(TriyaRu triyaRuToSave, JsonNode item) {
        JsonNode characteristics = item.get("properties");
        if (characteristics != null) {
            triyaRuToSave.setType(characteristics.get("Тип") != null ? characteristics.get("Тип").asText() : "-");
            triyaRuToSave.setLength(characteristics.get("Габаритная длина") != null ? convertToInteger(characteristics.get("Габаритная длина").asText()) : 0);
            triyaRuToSave.setWidth(characteristics.get("Габаритная ширина") != null ? convertToInteger(characteristics.get("Габаритная ширина").asText()) : 0);
            triyaRuToSave.setHeight(characteristics.get("Габаритная высота") != null ? convertToInteger(characteristics.get("Габаритная высота").asText()) : 0);
//            triyaRuToSave.setWeight(characteristics.get("Вес") != null ? convertToInteger(characteristics.get("Вес").asText()) : 0);
//            triyaRuToSave.setVolume(characteristics.get("Объем") != null ? convertVolumeToInteger(characteristics.get("Объем").asText()) : 0);
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
        return Integer.parseInt(text.replaceAll(" ", ""));
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
