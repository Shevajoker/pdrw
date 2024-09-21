package com.pdrw.pdrw.pinskdrev.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdrw.pdrw.pinskdrev.model.Pinskdrev;
import com.pdrw.pdrw.pinskdrev.repository.PinskdrevRepository;
import com.pdrw.pdrw.pinskdrev.service.PinskdrevDataService;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
@Service
public class PinskdrevDataServiceImpl implements PinskdrevDataService {

    private final ObjectMapper objectMapper;
    private final PinskdrevRepository pinskdrevRepository;

    public PinskdrevDataServiceImpl(ObjectMapper objectMapper,
                                    PinskdrevRepository pinskdrevRepository) {
        this.objectMapper = objectMapper;
        this.pinskdrevRepository = pinskdrevRepository;
    }


    @Override
    @Transactional
    public int setData(String data) {
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to load data", e);
        }
        int count = 0;
        JsonNode items = Optional.ofNullable(jsonNode).orElseThrow();
        for (JsonNode item : items) {
            Pinskdrev pinskdrevToSave = new Pinskdrev();
                    pinskdrevToSave.setArticle(item.get("data_id") != null ? item.get("data_id").asText() : "-");
                    pinskdrevToSave.setName(item.get("title") != null ? item.get("title").asText() : "-");
                    pinskdrevToSave.setLink(item.get("link") != null ? item.get("link").asText() : "-");
                    pinskdrevToSave.setImage(item.get("main_photo") != null ? item.get("main_photo").asText() : "-");
                    pinskdrevToSave.setPriceOld(item.get("old_price") != null ? BigDecimal.valueOf(item.get("old_price").asInt()) : BigDecimal.ZERO);
                    pinskdrevToSave.setPriceNew(item.get("price") != null ? BigDecimal.valueOf(item.get("price").asInt()) : BigDecimal.ZERO);
                    pinskdrevToSave.setDiscount(calculateDiscount(item));
                    pinskdrevToSave.setCreateDate(new Date());
                    pinskdrevToSave.setDateUpdate(new Date());
                    getCharacteristics(pinskdrevToSave, item);
            List<Pinskdrev> pinskdrevList = pinskdrevRepository.findByArticleOrderByDateUpdateDesc(pinskdrevToSave.getArticle());
            if (!pinskdrevList.isEmpty()) {
                Pinskdrev pinskdrev = pinskdrevList.getFirst();
                if (!Objects.equals(pinskdrevToSave.getPriceOld(), pinskdrev.getPriceOld())
                 && !Objects.equals(pinskdrevToSave.getPriceNew(), pinskdrev.getPriceNew())) {
                    pinskdrevToSave.setActual(Boolean.TRUE);
                    pinskdrevRepository.markActualFalse(pinskdrevToSave.getArticle());
                    pinskdrevRepository.save(pinskdrevToSave);
                    count++;
                } else {
                    pinskdrev.setDateUpdate(new Date());
                    pinskdrevRepository.save(pinskdrev);
                }
            } else {
                pinskdrevToSave.setActual(Boolean.TRUE);
                pinskdrevRepository.markActualFalse(pinskdrevToSave.getArticle());
                pinskdrevRepository.save(pinskdrevToSave);
                count++;
            }
        }

        return count;
    }

    private void getCharacteristics(Pinskdrev pinskdrevToSave, JsonNode item) {
        JsonNode characteristics = item.get("characteristics");
        if (characteristics != null) {
            pinskdrevToSave.setType(characteristics.get("Тип") != null ? characteristics.get("Тип").asText() : "-");
            pinskdrevToSave.setLength(characteristics.get("Длина (мм)") != null ? convertToInteger(characteristics.get("Длина (мм)").asText()) : 0);
            pinskdrevToSave.setWidth(characteristics.get("Ширина (мм)") != null ? convertToInteger(characteristics.get("Ширина (мм)").asText()) : 0);
            pinskdrevToSave.setHeight(characteristics.get("Высота (мм)") != null ? convertToInteger(characteristics.get("Высота (мм)").asText()) : 0);
            pinskdrevToSave.setWeight(characteristics.get("Вес") != null ? convertToInteger(characteristics.get("Вес").asText()) : 0);
            pinskdrevToSave.setVolume(characteristics.get("Объем") != null ? convertVolumeToInteger(characteristics.get("Объем").asText()) : 0);
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

        BigDecimal priceOld = item.get("old_price") != null ? BigDecimal.valueOf(item.get("old_price").asInt()) : BigDecimal.ZERO;
        BigDecimal priceNew = item.get("price") != null ? BigDecimal.valueOf(item.get("price").asInt()) : BigDecimal.ZERO;

        return priceOld.subtract(priceNew);
    }
}
