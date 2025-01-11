package com.pdrw.pdrw.pinskdrevby.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdrw.pdrw.pinskdrevby.model.ArchivePinskdrevBy;
import com.pdrw.pdrw.pinskdrevby.model.ArchivePinskdrevByDto;
import com.pdrw.pdrw.pinskdrevby.repository.ArchivePinskdrevByRepository;
import com.pdrw.pdrw.pinskdrevby.service.ArchivePinskdrevByService;
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
public class ArchivePinskdrevByServiceImpl implements ArchivePinskdrevByService {

    private final ObjectMapper objectMapper;
    private final ArchivePinskdrevByRepository archivePinskdrevByRepository;

    @Override
    @Transactional
    public void setData(String data) {
        if (data != null) {
            archivePinskdrevByRepository.deleteAllInBatch();
        }

        JsonNode node = null;
        try {
            File file = ResourceUtils.getFile(data);
            node = objectMapper.readTree(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert node != null;
        Map<ArchivePinskdrevBy, Integer> map = new HashMap<>();
        for (JsonNode jsonNode : node) {
            ArchivePinskdrevBy archivePinskdrevBy = new ArchivePinskdrevBy();
            archivePinskdrevBy.setAge(jsonNode.get(0).asInt());
            archivePinskdrevBy.setUrl(jsonNode.get(1).asText());
            archivePinskdrevBy.setDescription(jsonNode.get(2).asText());
            if (archivePinskdrevBy.getAge() > 2015) {
                if (!map.containsKey(archivePinskdrevBy)) {
                    map.put(archivePinskdrevBy, 1);
                } else {
                    map.put(archivePinskdrevBy, map.get(archivePinskdrevBy) + 1);
                }
            }
            log.info(archivePinskdrevBy);
        }
        log.info("=================MAP========================");
        log.info(map.toString());
        log.info("=================MAP========================");

        for (Map.Entry<ArchivePinskdrevBy, Integer> entry : map.entrySet()) {
            ArchivePinskdrevBy archivePinskdrevBy = entry.getKey();
            archivePinskdrevBy.setCount(entry.getValue());
            archivePinskdrevByRepository.save(archivePinskdrevBy);
        }


    }

    @Override
    public List<ArchivePinskdrevBy> getArchivePinskdrevBy() {
        List<ArchivePinskdrevBy> archivePinskdrevByList = archivePinskdrevByRepository.findAllOrder();
        List<String> groupedList = archivePinskdrevByRepository.findAllUrls();

        List<ArchivePinskdrevByDto> archivePinskdrevByDtos = archivePinskdrevByRepository.findAllDtos();

        return null;
    }

    private List<ArchivePinskdrevBy> fillFields(ArchivePinskdrevBy archivePinskdrevBy) {


        return null;
    }
}
