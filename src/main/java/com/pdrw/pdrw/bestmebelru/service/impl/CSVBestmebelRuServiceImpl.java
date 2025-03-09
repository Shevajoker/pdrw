package com.pdrw.pdrw.bestmebelru.service.impl;

import com.pdrw.pdrw.bestmebelru.model.BestmebelRu;
import com.pdrw.pdrw.bestmebelru.service.BestmebelRuService;
import com.pdrw.pdrw.bestmebelru.service.CSVBestmebelRuService;
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
public class CSVBestmebelRuServiceImpl implements CSVBestmebelRuService {
    private static final String SAMPLE_CSV_FILE = "./bestmebel-ru.csv";

    private final BestmebelRuService bestmebelRuService;

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
                    "actual",
                    "link");
            List<BestmebelRu> bestmebelRuList = bestmebelRuService.findAll();
            for (BestmebelRu bestmebelRu : bestmebelRuList) {
                csvPrinter.printRecord(
                        bestmebelRu.getId(),
                        bestmebelRu.getArticle(),
                        bestmebelRu.getName(),
                        bestmebelRu.getImage(),
                        bestmebelRu.getPriceNew(),
                        bestmebelRu.getPriceOld(),
                        bestmebelRu.getDiscount(),
                        bestmebelRu.getCreateDate(),
                        bestmebelRu.getDateUpdate(),
                        bestmebelRu.getActual(),
                        bestmebelRu.getLink()
                );
                csvPrinter.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Paths.get(SAMPLE_CSV_FILE);
    }
}
