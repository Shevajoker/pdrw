package com.pdrw.pdrw.pinskdrevby.service.impl;

import com.pdrw.pdrw.pinskdrevby.model.PinskdrevBy;
import com.pdrw.pdrw.pinskdrevby.service.CSVPinskdrevByService;
import com.pdrw.pdrw.pinskdrevby.service.PinskdrevByService;
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
public class CSVPinskdrevByServiceImpl implements CSVPinskdrevByService {
    private static final String SAMPLE_CSV_FILE = "./pinskdrev-ru.csv";

    private final PinskdrevByService pinskdrevByService;

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
            List<PinskdrevBy> pinskdrevRuList = pinskdrevByService.findAll();
            for (PinskdrevBy pinskdrevBy : pinskdrevRuList) {
                csvPrinter.printRecord(
                        pinskdrevBy.getId(),
                        pinskdrevBy.getArticle(),
                        pinskdrevBy.getName(),
                        pinskdrevBy.getImage(),
                        pinskdrevBy.getPriceNew(),
                        pinskdrevBy.getPriceOld(),
                        pinskdrevBy.getDiscount(),
                        pinskdrevBy.getCreateDate(),
                        pinskdrevBy.getDateUpdate(),
                        pinskdrevBy.getType(),
                        pinskdrevBy.getLength(),
                        pinskdrevBy.getWidth(),
                        pinskdrevBy.getHeight(),
                        pinskdrevBy.getWeight(),
                        pinskdrevBy.getVolume(),
                        pinskdrevBy.getActual(),
                        pinskdrevBy.getLink()
                );
                csvPrinter.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Paths.get(SAMPLE_CSV_FILE);
    }
}
