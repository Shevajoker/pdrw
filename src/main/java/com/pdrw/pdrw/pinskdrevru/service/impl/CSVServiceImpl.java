package com.pdrw.pdrw.pinskdrevru.service.impl;

import com.pdrw.pdrw.pinskdrevru.model.PinskdrevRu;
import com.pdrw.pdrw.pinskdrevru.service.CSVService;
import com.pdrw.pdrw.pinskdrevru.service.PinskdrevRuService;
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
public class CSVServiceImpl implements CSVService {
    private static final String SAMPLE_CSV_FILE = "./pinskdrev-ru.csv";

    private final PinskdrevRuService pinskdrevRuService;

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
            List<PinskdrevRu> pinskdrevRuList = pinskdrevRuService.findAll();
            for (PinskdrevRu pinskdrevRu : pinskdrevRuList) {
                csvPrinter.printRecord(
                        pinskdrevRu.getId(),
                        pinskdrevRu.getArticle(),
                        pinskdrevRu.getName(),
                        pinskdrevRu.getImage(),
                        pinskdrevRu.getPriceNew(),
                        pinskdrevRu.getPriceOld(),
                        pinskdrevRu.getDiscount(),
                        pinskdrevRu.getCreateDate(),
                        pinskdrevRu.getDateUpdate(),
                        pinskdrevRu.getType(),
                        pinskdrevRu.getLength(),
                        pinskdrevRu.getWidth(),
                        pinskdrevRu.getHeight(),
                        pinskdrevRu.getWeight(),
                        pinskdrevRu.getVolume(),
                        pinskdrevRu.getActual(),
                        pinskdrevRu.getLink()
                );
                csvPrinter.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Paths.get(SAMPLE_CSV_FILE);
    }
}
