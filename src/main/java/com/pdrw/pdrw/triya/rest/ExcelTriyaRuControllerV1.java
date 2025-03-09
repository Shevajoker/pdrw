package com.pdrw.pdrw.triya.rest;

import com.pdrw.pdrw.triya.service.ExcelTriyaRuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;

@Log4j2
@RestController
@RequestMapping("/api/v1/triya-ru/download")
@RequiredArgsConstructor
@Tag(name = "TriyaRu")
public class ExcelTriyaRuControllerV1 {

    private final ExcelTriyaRuService excelService;

    @Operation(summary = "Download excel file")
    @GetMapping("/excel")
    public ResponseEntity<?> getExcel() {
        Path file = excelService.getExcelFile();

        InputStreamResource resource = null;
        try {
            resource = new InputStreamResource(new FileInputStream(file.getFileName().toString()));
        } catch (FileNotFoundException e) {
            log.info("RuntimeException(e): File not found!!!");
            throw new RuntimeException(e);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFileName().toString());
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);

    }

}
