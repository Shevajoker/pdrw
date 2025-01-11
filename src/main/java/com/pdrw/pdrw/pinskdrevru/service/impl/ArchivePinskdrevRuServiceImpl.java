package com.pdrw.pdrw.pinskdrevru.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdrw.pdrw.pinskdrevru.model.ArchivePinskdrevRu;
import com.pdrw.pdrw.pinskdrevru.model.ArchivePinskdrevRuDto;
import com.pdrw.pdrw.pinskdrevru.repository.ArchivePinskdrevRuRepository;
import com.pdrw.pdrw.pinskdrevru.service.ArchivePinskdrevRuService;
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
public class ArchivePinskdrevRuServiceImpl implements ArchivePinskdrevRuService {

    private final ObjectMapper objectMapper;
    private final ArchivePinskdrevRuRepository archivePinskdrevRuRepository;

    @Override
    @Transactional
    public void setData(String data) {
        if (data != null) {
            archivePinskdrevRuRepository.deleteAllInBatch();
        }

        JsonNode node = null;
        try {
            File file = ResourceUtils.getFile(data);
            node = objectMapper.readTree(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert node != null;
        Map<ArchivePinskdrevRu, Integer> map = new HashMap<>();
        for (JsonNode jsonNode : node) {
            ArchivePinskdrevRu archivePinskdrevRu = new ArchivePinskdrevRu();
            archivePinskdrevRu.setAge(jsonNode.get(0).asInt());
            archivePinskdrevRu.setUrl(jsonNode.get(1).asText());
            archivePinskdrevRu.setDescription(jsonNode.get(2).asText());
            if (archivePinskdrevRu.getAge() > 2015) {
                if (!map.containsKey(archivePinskdrevRu)) {
                    map.put(archivePinskdrevRu, 1);
                } else {
                    map.put(archivePinskdrevRu, map.get(archivePinskdrevRu) + 1);
                }
            }
            log.info(archivePinskdrevRu);
        }
        log.info("=================MAP========================");
        log.info(map.toString());
        log.info("=================MAP========================");

        for (Map.Entry<ArchivePinskdrevRu, Integer> entry : map.entrySet()) {
            ArchivePinskdrevRu archivePinskdrevRu = entry.getKey();
            archivePinskdrevRu.setCount(entry.getValue());
            archivePinskdrevRuRepository.save(archivePinskdrevRu);
        }


    }

    @Override
    public List<ArchivePinskdrevRu> getArchivePinskdrevRu() {
        List<ArchivePinskdrevRu> archivePinskdrevRuList = archivePinskdrevRuRepository.findAllOrder();
        List<String> groupedList = archivePinskdrevRuRepository.findAllUrls();

        List<ArchivePinskdrevRuDto> archivePinskdrevRuDtos = archivePinskdrevRuRepository.findAllDtos();

        return null;
    }

    private List<ArchivePinskdrevRu> fillFields(ArchivePinskdrevRu archivePinskdrevRu) {


        return null;
    }
}
