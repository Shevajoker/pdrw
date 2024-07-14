package com.pdrw.pdrw.hoff.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdrw.pdrw.hoff.model.HoffItem;
import com.pdrw.pdrw.hoff.service.GetHoffDataService;
import com.pdrw.pdrw.hoff.service.HoffItemService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GetHoffDataServiceImpl implements GetHoffDataService {

    private final HoffItemService hoffItemService;
    private final ObjectMapper objectMapper;

    public GetHoffDataServiceImpl(HoffItemService hoffItemService,
                                  ObjectMapper objectMapper) {
        this.hoffItemService = hoffItemService;
        this.objectMapper = objectMapper;
    }

    @Override
    public int setData(String data) throws IOException {
        JsonNode jsonNode;
        try {
            System.out.println("objectMapper.readTree(inputStream)");
            jsonNode = objectMapper.readTree(data);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load data", e);
        }
        int count = 0;
        JsonNode items = Optional.ofNullable(jsonNode)
                .map(j -> j.get("data"))
                .map(j -> j.get("items")).orElseThrow(() -> new IOException("items not found"));
        for (JsonNode item : items) {
            HoffItem hoffItemToSave = createItem(item);
            List<HoffItem> hoffItemList = hoffItemService.findByArticleOrderByDateUpdateDesc(hoffItemToSave.getArticle());
            if (!hoffItemList.isEmpty()) {
                HoffItem hoffItem = hoffItemList.getFirst();
                    if (!Objects.equals(hoffItem.getNewPrice(), hoffItemToSave.getNewPrice())
                            || !Objects.equals(hoffItem.getOldPrice(), hoffItemToSave.getOldPrice())) {
                        hoffItemService.createItem(createItem(item));
                        count++;
                    }
            } else {
                hoffItemService.createItem(hoffItemToSave);
                count++;
            }
        }
        return count;
    }

    private HoffItem createItem(JsonNode itemNode) {
        HoffItem result = new HoffItem()
                .setArticle(itemNode.get("id") != null ? itemNode.get("id").asText() : "-")
                .setName(itemNode.get("name") != null ? itemNode.get("name").asText() : "-")
                .setImage(itemNode.get("image") != null ? itemNode.get("image").asText() : "-")
                .setDiscount(itemNode.get("discount") != null ? itemNode.get("discount").asInt() : 0);
        extractPrice(itemNode, result);
        result.setDateUpdate(new Date());
        return result;
    }

    private HoffItem extractPrice(JsonNode node, HoffItem item) {
        JsonNode priceNode = node.get("prices");
        if (priceNode == null) {
            return item.setOldPrice(0)
                    .setNewPrice(0);
        }
        return item.setNewPrice(priceNode.get("new") != null ? priceNode.get("new").asInt() : 0)
                .setOldPrice(priceNode.get("old") != null ? priceNode.get("old").asInt() : 0);
    }

    @Override
    public List<HoffItem> getData(Integer limit) {
        return hoffItemService.findAll();
    }
}
