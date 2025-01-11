package com.pdrw.pdrw.pinskdrevby.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdrw.pdrw.pinskdrevby.model.PinskdrevBy;
import com.pdrw.pdrw.pinskdrevby.repository.PinskdrevByRepository;
import com.pdrw.pdrw.pinskdrevby.service.PinskdrevByDataService;
import com.pdrw.pdrw.pinskdrevru.client.AlertClient;
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
public class PinskdrevByDataServiceImpl implements PinskdrevByDataService {

    public static final String DEFAULT_STRING_VALUE = "-";
    private final ObjectMapper objectMapper;
    private final PinskdrevByRepository pinskdrevByRepository;
    private final AlertClient alertClient;

    public PinskdrevByDataServiceImpl(ObjectMapper objectMapper,
                                      PinskdrevByRepository pinskdrevByRepository,
                                      AlertClient alertClient) {
        this.objectMapper = objectMapper;
        this.pinskdrevByRepository = pinskdrevByRepository;
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
            PinskdrevBy pinskdrevByToSave = new PinskdrevBy();
            pinskdrevByToSave.setArticle(item.get("dataId") != null ? item.get("dataId").asText() : DEFAULT_STRING_VALUE);
            pinskdrevByToSave.setName(item.get("nameProduct") != null ? item.get("nameProduct").asText() : "-");
            pinskdrevByToSave.setLink(item.get("uriProduct") != null ? item.get("uriProduct").asText() : "-");
            pinskdrevByToSave.setImage(item.get("photo") != null ? item.get("photo").asText() : "-");
            pinskdrevByToSave.setPriceOld(item.get("oldPrice") != null ? BigDecimal.valueOf(item.get("oldPrice").asInt()) : BigDecimal.ZERO);
            pinskdrevByToSave.setPriceNew(item.get("price") != null ? BigDecimal.valueOf(item.get("price").asInt()) : BigDecimal.ZERO);
            pinskdrevByToSave.setDiscount(calculateDiscount(item));
            pinskdrevByToSave.setCreateDate(new Date());
            pinskdrevByToSave.setDateUpdate(new Date());
            getCharacteristics(pinskdrevByToSave, item);
            validateItem(pinskdrevByToSave, message);
            List<PinskdrevBy> pinskdrevByList = pinskdrevByRepository.findByArticleOrderByDateUpdateDesc(pinskdrevByToSave.getArticle());
            if (!pinskdrevByList.isEmpty()) {
                PinskdrevBy pinskdrevBy = pinskdrevByList.getFirst();
                if (!Objects.equals(pinskdrevByToSave.getPriceOld(), pinskdrevBy.getPriceOld())
                        && !Objects.equals(pinskdrevByToSave.getPriceNew(), pinskdrevBy.getPriceNew())) {
                    pinskdrevByToSave.setActual(Boolean.TRUE);
                    pinskdrevByRepository.markActualFalse(pinskdrevByToSave.getArticle());
                    pinskdrevByRepository.save(pinskdrevByToSave);
                    count++;
                } else {
                    pinskdrevBy.setDateUpdate(new Date());
                    pinskdrevByRepository.save(pinskdrevBy);
                }
            } else {
                pinskdrevByToSave.setActual(Boolean.TRUE);
                pinskdrevByRepository.markActualFalse(pinskdrevByToSave.getArticle());
                pinskdrevByRepository.save(pinskdrevByToSave);
                count++;
            }
        }
        return count;
    }

    private void validateItem(PinskdrevBy pinskdrevByToSave, StringBuilder message) {
        if (pinskdrevByToSave.getArticle().equals(DEFAULT_STRING_VALUE)) {
            message.append("- no dataId -");
        }
        if (pinskdrevByToSave.getName().equals(DEFAULT_STRING_VALUE)) {
            message.append("- no nameProduct -");
        }
        if (pinskdrevByToSave.getLink().equals(DEFAULT_STRING_VALUE)) {
            message.append("- no uriProduct -");
        }
        if (pinskdrevByToSave.getImage().equals(DEFAULT_STRING_VALUE)) {
            message.append("- no photo -");
        }
        if (pinskdrevByToSave.getPriceNew().compareTo(BigDecimal.ZERO) == 0) {
            message.append("- no price -");
        }
        if (!message.isEmpty()) {
            message.append(System.lineSeparator());
        }
        log.info(message.toString());
    }

    private void getCharacteristics(PinskdrevBy pinskdrevByToSave, JsonNode item) {
        JsonNode characteristics = item.get("properties");
        if (characteristics != null) {
            pinskdrevByToSave.setType(characteristics.get("Тип") != null ? characteristics.get("Тип").asText() : "-");
            pinskdrevByToSave.setLength(characteristics.get("Длина (мм)") != null ? convertToInteger(characteristics.get("Длина (мм)").asText()) : 0);
            pinskdrevByToSave.setWidth(characteristics.get("Ширина (мм)") != null ? convertToInteger(characteristics.get("Ширина (мм)").asText()) : 0);
            pinskdrevByToSave.setHeight(characteristics.get("Высота (мм)") != null ? convertToInteger(characteristics.get("Высота (мм)").asText()) : 0);
            pinskdrevByToSave.setWeight(characteristics.get("Вес") != null ? convertToInteger(characteristics.get("Вес").asText()) : 0);
            pinskdrevByToSave.setVolume(characteristics.get("Объем") != null ? convertVolumeToInteger(characteristics.get("Объем").asText()) : 0);
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
