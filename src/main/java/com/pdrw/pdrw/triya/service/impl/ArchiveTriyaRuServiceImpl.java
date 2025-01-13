package com.pdrw.pdrw.triya.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdrw.pdrw.triya.model.ArchiveTriyaRu;
import com.pdrw.pdrw.triya.model.ArchiveTriyaRuDto;
import com.pdrw.pdrw.triya.repository.ArchiveTriyaRuRepository;
import com.pdrw.pdrw.triya.service.ArchiveTriyaRuService;
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
public class ArchiveTriyaRuServiceImpl implements ArchiveTriyaRuService {

    private final ObjectMapper objectMapper;
    private final ArchiveTriyaRuRepository archiveTriyaRuRepository;

    @Override
    @Transactional
    public void setData(String data) {
        if (data != null) {
            archiveTriyaRuRepository.deleteAllInBatch();
        }

        JsonNode node = null;
        try {
            File file = ResourceUtils.getFile(data);
            node = objectMapper.readTree(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert node != null;
        Map<ArchiveTriyaRu, Integer> map = new HashMap<>();
        for (JsonNode jsonNode : node) {
            ArchiveTriyaRu archiveTriyaRu = new ArchiveTriyaRu();
            archiveTriyaRu.setAge(jsonNode.get(0).asInt());
            archiveTriyaRu.setUrl(jsonNode.get(1).asText());
            archiveTriyaRu.setDescription(jsonNode.get(2).asText());
            if (archiveTriyaRu.getAge() > 2015) {
                if (!map.containsKey(archiveTriyaRu)) {
                    map.put(archiveTriyaRu, 1);
                } else {
                    map.put(archiveTriyaRu, map.get(archiveTriyaRu) + 1);
                }
            }
            log.info(archiveTriyaRu);
        }
        log.info("=================MAP========================");
        log.info(map.toString());
        log.info("=================MAP========================");

        for (Map.Entry<ArchiveTriyaRu, Integer> entry : map.entrySet()) {
            ArchiveTriyaRu archiveTriyaRu = entry.getKey();
            archiveTriyaRu.setCount(entry.getValue());
            archiveTriyaRuRepository.save(archiveTriyaRu);
        }


    }

    @Override
    public List<ArchiveTriyaRu> getArchiveTriyaRu() {
        List<ArchiveTriyaRu> archiveTriyaRuList = archiveTriyaRuRepository.findAllOrder();
        List<String> groupedList = archiveTriyaRuRepository.findAllUrls();

        List<ArchiveTriyaRuDto> archiveTriyaRuDtos = archiveTriyaRuRepository.findAllDtos();

        return null;
    }

    private List<ArchiveTriyaRu> fillFields(ArchiveTriyaRu archiveTriyaRu) {
        return null;
    }
}
