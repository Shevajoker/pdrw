package com.pdrw.pdrw.triya.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdrw.pdrw.triya.model.Triya;
import com.pdrw.pdrw.triya.repository.TriyaRepository;
import com.pdrw.pdrw.triya.service.TriyaDataService;
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
public class TriyaDataServiceImpl implements TriyaDataService {

    private final ObjectMapper objectMapper;
    private final TriyaRepository triyaRepository;

    public TriyaDataServiceImpl(ObjectMapper objectMapper,
                                TriyaRepository triyaRepository) {
        this.objectMapper = objectMapper;
        this.triyaRepository = triyaRepository;
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
            Triya triyaToSave = Triya.builder()
                    .article(item.get("url") != null ? item.get("url").asText().split("model=")[1] : "-")
                    .name(item.get("title") != null ? item.get("title").asText() : "-")
                    .link(item.get("url") != null ? item.get("url").asText() : "-")
                    .image(item.get("main_image_url") != null ? item.get("main_image_url").asText() : "-")
                    .priceOld(item.get("old_price") != null ? BigDecimal.valueOf(item.get("old_price").asInt()) : BigDecimal.ZERO)
                    .priceNew(item.get("price") != null ? BigDecimal.valueOf(Integer.parseInt(item.get("price").asText().replaceAll(" ", ""))) : BigDecimal.ZERO)
                    .discount(calculateDiscount(item))
                    .createDate(new Date())
                    .dateUpdate(new Date())
                    .build();
            getCharacteristics(triyaToSave, item);
            List<Triya> triyaList = triyaRepository.findByArticleOrderByDateUpdateDesc(triyaToSave.getArticle());
            if (!triyaList.isEmpty()) {
                Triya triya = triyaList.getFirst();
                if (!Objects.equals(triyaToSave.getPriceOld(), triya.getPriceOld())
                        && !Objects.equals(triyaToSave.getPriceNew(), triya.getPriceNew())) {
                    triyaToSave.setActual(Boolean.TRUE);
                    triyaRepository.markActualFalse(triyaToSave.getArticle());
                    triyaRepository.save(triyaToSave);
                    count++;
                } else {
                    triya.setDateUpdate(new Date());
                    triyaRepository.save(triya);
                }
            } else {
                triyaToSave.setActual(Boolean.TRUE);
                triyaRepository.markActualFalse(triyaToSave.getArticle());
                triyaRepository.save(triyaToSave);
                count++;
            }
        }

        return count;
    }

    private void getCharacteristics(Triya triya, JsonNode item) {
        JsonNode characteristics = item.get("characteristics");
        if (characteristics != null) {
            JsonNode dimensions = characteristics.get("Размеры (мм)");
            if (dimensions != null) {
                triya.setLength(characteristics.get("Габаритная глубина") != null ? convertToInteger(characteristics.get("Габаритная глубина").asText()) : 0);
                triya.setWidth(characteristics.get("Габаритная ширина") != null ? convertToInteger(characteristics.get("Габаритная ширина").asText()) : 0);
                triya.setHeight(characteristics.get("Габаритная высота") != null ? convertToInteger(characteristics.get("\"Габаритная высота\"").asText()) : 0);

                List<String> length =  dimensions.findValuesAsText("Габаритная глубина");
                List<String> width =  dimensions.findValuesAsText("Габаритная ширина");
                List<String> weight =  dimensions.findValuesAsText("Габаритная высота");
                if (length != null && !length.isEmpty()) {
                    triya.setLength(Integer.parseInt(length.getFirst().replaceAll(" ", "")));
                }
                if (width != null && !width.isEmpty()) {
                    triya.setWidth(Integer.parseInt(width.getFirst().replaceAll(" ", "")));
                }
                if (weight != null && !weight.isEmpty()) {
                    triya.setWeight(Integer.parseInt(weight.getFirst().replaceAll(" ", "")));
                }
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

        BigDecimal priceOld = item.get("old_price") != null ? BigDecimal.valueOf(item.get("old_price").asInt()) : BigDecimal.ZERO;
        BigDecimal priceNew = item.get("price") != null ? BigDecimal.valueOf(Integer.parseInt(item.get("price").asText().replaceAll(" ", ""))) : BigDecimal.ZERO;

        return priceOld.subtract(priceNew);
    }
}
