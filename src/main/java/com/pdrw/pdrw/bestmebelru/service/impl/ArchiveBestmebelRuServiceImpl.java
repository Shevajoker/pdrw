package com.pdrw.pdrw.bestmebelru.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdrw.pdrw.bestmebelru.model.ArchiveBestmebelRu;
import com.pdrw.pdrw.bestmebelru.model.ArchiveBestmebelRuDto;
import com.pdrw.pdrw.bestmebelru.repository.ArchiveBestmebelRuRepository;
import com.pdrw.pdrw.bestmebelru.service.ArchiveBestmebelRuService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class ArchiveBestmebelRuServiceImpl implements ArchiveBestmebelRuService {

    private final ObjectMapper objectMapper;
    private final ArchiveBestmebelRuRepository archiveBestmebelRuRepository;

    @Override
    @Transactional
    public void setData(String data) {
        if (data != null) {
            archiveBestmebelRuRepository.deleteAllInBatch();
        }

        JsonNode node = null;
        try {
            File file = ResourceUtils.getFile(data);
            node = objectMapper.readTree(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert node != null;
        Map<ArchiveBestmebelRu, Integer> map = new HashMap<>();
        for (JsonNode jsonNode : node) {
            ArchiveBestmebelRu archiveBestmebelRu = new ArchiveBestmebelRu();
            archiveBestmebelRu.setAge(jsonNode.get(0).asInt());
            archiveBestmebelRu.setUrl(jsonNode.get(1).asText());
            archiveBestmebelRu.setDescription(jsonNode.get(2).asText());
            if (archiveBestmebelRu.getAge() > 2015) {
                if (!map.containsKey(archiveBestmebelRu)) {
                    map.put(archiveBestmebelRu, 1);
                } else {
                    map.put(archiveBestmebelRu, map.get(archiveBestmebelRu) + 1);
                }
            }
            log.info(archiveBestmebelRu);
        }
        log.info("=================MAP========================");
        log.info(map.toString());
        log.info("=================MAP========================");

        for (Map.Entry<ArchiveBestmebelRu, Integer> entry : map.entrySet()) {
            ArchiveBestmebelRu archiveBestmebelRu = entry.getKey();
            archiveBestmebelRu.setCount(entry.getValue());
            archiveBestmebelRuRepository.save(archiveBestmebelRu);
        }


    }

    @Override
    public List<ArchiveBestmebelRu> getArchiveBestmebelRu() {
        List<ArchiveBestmebelRu> archiveBestmebelRuList = archiveBestmebelRuRepository.findAllOrder();
        List<String> groupedList = archiveBestmebelRuRepository.findAllUrls();

        List<ArchiveBestmebelRuDto> archiveBestmebelRuDtos = archiveBestmebelRuRepository.findAllDtos();

        return null;
    }

    private List<ArchiveBestmebelRu> fillFields(ArchiveBestmebelRu archiveBestmebelRu) {


        return null;
    }
}
