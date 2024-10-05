package com.pdrw.pdrw.angstrem.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdrw.pdrw.angstrem.model.Angstrem;
import com.pdrw.pdrw.angstrem.repository.AngstremRepository;
import com.pdrw.pdrw.angstrem.service.AngstremDataService;
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
public class AngstremDataServiceImpl implements AngstremDataService {

    private final ObjectMapper objectMapper;
    private final AngstremRepository angstremRepository;

    public AngstremDataServiceImpl(ObjectMapper objectMapper,
                                   AngstremRepository angstremRepository) {
        this.objectMapper = objectMapper;
        this.angstremRepository = angstremRepository;
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
            Angstrem angstremToSave = Angstrem.builder()
                    .article(item.get("dataId") != null ? item.get("dataId").asText() : "-")
                    .name(item.get("nameProduct") != null ? item.get("nameProduct").asText() : "-")
                    .link(item.get("uriProduct") != null ? item.get("uriProduct").asText() : "-")
                    .image(item.get("photo") != null ? item.get("photo").asText() : "-")
                    .priceOld(item.get("oldPrice") != null ? BigDecimal.valueOf(item.get("oldPrice").asInt()) : BigDecimal.ZERO)
                    .priceNew(item.get("price") != null ? BigDecimal.valueOf(Integer.parseInt(item.get("price").asText().replaceAll(" ", ""))) : BigDecimal.ZERO)
                    .discount(calculateDiscount(item))
                    .createDate(new Date())
                    .dateUpdate(new Date())
                    .build();
            getCharacteristics(angstremToSave, item);
            List<Angstrem> angstremList = angstremRepository.findByArticleOrderByDateUpdateDesc(angstremToSave.getArticle());
            if (!angstremList.isEmpty()) {
                Angstrem angstrem = angstremList.getFirst();
                if (!Objects.equals(angstremToSave.getPriceOld(), angstrem.getPriceOld())
                        && !Objects.equals(angstremToSave.getPriceNew(), angstrem.getPriceNew())) {
                    angstremToSave.setActual(Boolean.TRUE);
                    angstremRepository.markActualFalse(angstremToSave.getArticle());
                    angstremRepository.save(angstremToSave);
                    count++;
                } else {
                    angstrem.setDateUpdate(new Date());
                    angstremRepository.save(angstrem);
                }
            } else {
                angstremToSave.setActual(Boolean.TRUE);
                angstremRepository.markActualFalse(angstremToSave.getArticle());
                angstremRepository.save(angstremToSave);
                count++;
            }
        }

        return count;
    }

    private void getCharacteristics(Angstrem angstrem, JsonNode item) {
        JsonNode characteristics = item.get("properties");
        if (characteristics != null) {
                List<String> length = characteristics.findValuesAsText("Габаритная глубина");
                List<String> width = characteristics.findValuesAsText("Габаритная ширина");
                List<String> weight = characteristics.findValuesAsText("Габаритная высота");
                List<String> category = characteristics.findValuesAsText("Категории");
                if (length != null && !length.isEmpty()) {
                    angstrem.setLength(Integer.parseInt(length.getFirst().replaceAll(" ", "")));
                }
                if (width != null && !width.isEmpty()) {
                    angstrem.setWidth(Integer.parseInt(width.getFirst().replaceAll(" ", "")));
                }
                if (weight != null && !weight.isEmpty()) {
                    angstrem.setWeight(Integer.parseInt(weight.getFirst().replaceAll(" ", "")));
                }
                if (category != null && !category.isEmpty()) {
                    angstrem.setType(category.getFirst());
                }
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
        text = text.replaceAll(" ", "");
        Pattern pattern = Pattern.compile("[0-9]");
        Matcher matcher = pattern.matcher(text);
        String trimmedText = "0";
        if (matcher.find()) {
            trimmedText = matcher.group();
        }
        return Integer.parseInt(trimmedText);
    }

    private BigDecimal calculateDiscount(JsonNode item) {

        BigDecimal priceOld = item.get("oldPrice") != null ? BigDecimal.valueOf(item.get("oldPrice").asInt()) : BigDecimal.ZERO;
        BigDecimal priceNew = item.get("price") != null ? BigDecimal.valueOf(Integer.parseInt(item.get("price").asText().replaceAll(" ", ""))) : BigDecimal.ZERO;

        return priceOld.subtract(priceNew);
    }
}
