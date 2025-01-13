package com.pdrw.pdrw.triya.service.impl;


import com.pdrw.pdrw.triya.model.TriyaRu;
import com.pdrw.pdrw.triya.service.CSVTriyaRuService;
import com.pdrw.pdrw.triya.service.TriyaRuService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CSVTriyaRuServiceImpl implements CSVTriyaRuService {
    private static final String SAMPLE_CSV_FILE = "./triya-ru.csv";

    private final TriyaRuService triyaRuService;

    @Override
    public Path getCSVPath() {

        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(SAMPLE_CSV_FILE), StandardCharsets.UTF_8);
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withDelimiter(';'));
            csvPrinter.printRecord("id",
                    "article",
                    "name",
                    "image",
                    "price_new",
                    "price_old",
                    "discount",
                    "date_create",
                    "date_update",
                    "type",
                    "length",
                    "width",
                    "height",
                    "weight",
                    "volume",
                    "actual",
                    "link");
            List<TriyaRu> triyaRuList = triyaRuService.findAll();
            for (TriyaRu triyaRu : triyaRuList) {
                csvPrinter.printRecord(
                        triyaRu.getId(),
                        triyaRu.getArticle(),
                        triyaRu.getName(),
                        triyaRu.getImage(),
                        triyaRu.getPriceNew(),
                        triyaRu.getPriceOld(),
                        triyaRu.getDiscount(),
                        triyaRu.getCreateDate(),
                        triyaRu.getDateUpdate(),
                        triyaRu.getType(),
                        triyaRu.getLength(),
                        triyaRu.getWidth(),
                        triyaRu.getHeight(),
                        triyaRu.getWeight(),
                        triyaRu.getVolume(),
                        triyaRu.getActual(),
                        triyaRu.getLink()
                );
                csvPrinter.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Paths.get(SAMPLE_CSV_FILE);
    }
}
