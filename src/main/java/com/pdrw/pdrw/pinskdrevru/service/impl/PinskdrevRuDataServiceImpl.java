package com.pdrw.pdrw.pinskdrevru.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdrw.pdrw.pinskdrevru.client.AlertClient;
import com.pdrw.pdrw.pinskdrevru.model.PinskdrevRu;
import com.pdrw.pdrw.pinskdrevru.repository.PinskdrevRuRepository;
import com.pdrw.pdrw.pinskdrevru.service.PinskdrevRuDataService;
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
public class PinskdrevRuDataServiceImpl implements PinskdrevRuDataService {

    public static final String DEFAULT_STRING_VALUE = "-";
    private final ObjectMapper objectMapper;
    private final PinskdrevRuRepository pinskdrevRuRepository;
    private final AlertClient alertClient;

    public PinskdrevRuDataServiceImpl(ObjectMapper objectMapper,
                                      PinskdrevRuRepository pinskdrevRuRepository,
                                      AlertClient alertClient) {
        this.objectMapper = objectMapper;
        this.pinskdrevRuRepository = pinskdrevRuRepository;
        this.alertClient = alertClient;
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
            PinskdrevRu pinskdrevRuToSave = new PinskdrevRu();
            pinskdrevRuToSave.setArticle(item.get("dataId") != null ? item.get("dataId").asText() : DEFAULT_STRING_VALUE);
            pinskdrevRuToSave.setName(item.get("nameProduct") != null ? item.get("nameProduct").asText() : "-");
            pinskdrevRuToSave.setLink(item.get("uriProduct") != null ? item.get("uriProduct").asText() : "-");
            pinskdrevRuToSave.setImage(item.get("photo") != null ? item.get("photo").asText() : "-");
            pinskdrevRuToSave.setPriceOld(item.get("oldPrice") != null ? BigDecimal.valueOf(item.get("oldPrice").asInt()) : BigDecimal.ZERO);
            pinskdrevRuToSave.setPriceNew(item.get("price") != null ? BigDecimal.valueOf(item.get("price").asInt()) : BigDecimal.ZERO);
            pinskdrevRuToSave.setDiscount(calculateDiscount(item));
            pinskdrevRuToSave.setCreateDate(new Date());
            pinskdrevRuToSave.setDateUpdate(new Date());
            getCharacteristics(pinskdrevRuToSave, item);
            validateItem(pinskdrevRuToSave, message);
            List<PinskdrevRu> pinskdrevRuList = pinskdrevRuRepository.findByArticleOrderByDateUpdateDesc(pinskdrevRuToSave.getArticle());
            if (!pinskdrevRuList.isEmpty()) {
                PinskdrevRu pinskdrevRu = pinskdrevRuList.getFirst();
                if (!Objects.equals(pinskdrevRuToSave.getPriceOld(), pinskdrevRu.getPriceOld())
                        && !Objects.equals(pinskdrevRuToSave.getPriceNew(), pinskdrevRu.getPriceNew())) {
                    pinskdrevRuToSave.setActual(Boolean.TRUE);
                    pinskdrevRuRepository.markActualFalse(pinskdrevRuToSave.getArticle());
                    pinskdrevRuRepository.save(pinskdrevRuToSave);
                    count++;
                } else {
                    pinskdrevRu.setDateUpdate(new Date());
                    pinskdrevRuRepository.save(pinskdrevRu);
                }
            } else {
                pinskdrevRuToSave.setActual(Boolean.TRUE);
                pinskdrevRuRepository.markActualFalse(pinskdrevRuToSave.getArticle());
                pinskdrevRuRepository.save(pinskdrevRuToSave);
                count++;
            }
        }
        return count;
    }

    private void validateItem(PinskdrevRu pinskdrevRuToSave, StringBuilder message) {
        if (pinskdrevRuToSave.getArticle().equals(DEFAULT_STRING_VALUE)) {
            message.append("- no dataId -");
        }
        if (pinskdrevRuToSave.getName().equals(DEFAULT_STRING_VALUE)) {
            message.append("- no nameProduct -");
        }
        if (pinskdrevRuToSave.getLink().equals(DEFAULT_STRING_VALUE)) {
            message.append("- no uriProduct -");
        }
        if (pinskdrevRuToSave.getImage().equals(DEFAULT_STRING_VALUE)) {
            message.append("- no photo -");
        }
        if (pinskdrevRuToSave.getPriceNew().compareTo(BigDecimal.ZERO) == 0) {
            message.append("- no price -");
        }
        if (!message.isEmpty()) {
            message.append(System.lineSeparator());
        }
        log.info(message.toString());
    }

    private void getCharacteristics(PinskdrevRu pinskdrevRuToSave, JsonNode item) {
        JsonNode characteristics = item.get("properties");
        if (characteristics != null) {
            pinskdrevRuToSave.setType(characteristics.get("Тип") != null ? characteristics.get("Тип").asText() : "-");
            pinskdrevRuToSave.setLength(characteristics.get("Длина (мм)") != null ? convertToInteger(characteristics.get("Длина (мм)").asText()) : 0);
            pinskdrevRuToSave.setWidth(characteristics.get("Ширина (мм)") != null ? convertToInteger(characteristics.get("Ширина (мм)").asText()) : 0);
            pinskdrevRuToSave.setHeight(characteristics.get("Высота (мм)") != null ? convertToInteger(characteristics.get("Высота (мм)").asText()) : 0);
            pinskdrevRuToSave.setWeight(characteristics.get("Вес") != null ? convertToInteger(characteristics.get("Вес").asText()) : 0);
            pinskdrevRuToSave.setVolume(characteristics.get("Объем") != null ? convertVolumeToInteger(characteristics.get("Объем").asText()) : 0);
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
