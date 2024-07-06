package com.pdrw.pdrw.loader;

import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdrw.pdrw.model.Item;
import com.pdrw.pdrw.service.ItemService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DataLoader implements CommandLineRunner {
    private final ObjectMapper objectMapper;
    private final ItemService itemService;

    public DataLoader(ObjectMapper objectMapper,
                      ItemService itemService) {
        this.objectMapper = objectMapper;
        this.itemService = itemService;
    }

    @Override
    public void run(String... args) throws Exception {
        JsonNode json;
        List<JsonNode> jsonNodes;
        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/all_data.json")) {
            json = objectMapper.readValue(inputStream, JsonNode.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load data", e);
        }

        int count = 0;
        List<Item> itemList = new ArrayList<>();
        JsonNode items = Optional.ofNullable(json)
                .map(j -> j.get("data"))
                .map(j -> j.get("items")).orElseThrow(() -> new IOException("items not found"));
        for (JsonNode item : items) {
            itemList.add(createItem(item));
            itemService.createItem(createItem(item));
            count++;
        }
    }

    private Item createItem(JsonNode itemNode) {
        Item result = new Item()
                .setId(itemNode.get("id") != null ? itemNode.get("id").asText() : "-")
                .setName(itemNode.get("name") != null ? itemNode.get("name").asText() : "-")
                .setImage(itemNode.get("image") != null ? itemNode.get("image").asText() : "-")
                .setDiscount(itemNode.get("discount") != null ? itemNode.get("discount").asInt() : 0);
        extractPrice(itemNode, result);
        return result;
    }

    private Item extractPrice(JsonNode node, Item item) {
        JsonNode priceNode = node.get("prices");
        if (priceNode == null) {
            return item.setOldPrice(0)
                    .setNewPrice(0);
        }
        return item.setNewPrice(priceNode.get("new") != null ? priceNode.get("new").asInt() : 0)
                .setOldPrice(priceNode.get("old") != null ? priceNode.get("old").asInt() : 0);
    }
}
