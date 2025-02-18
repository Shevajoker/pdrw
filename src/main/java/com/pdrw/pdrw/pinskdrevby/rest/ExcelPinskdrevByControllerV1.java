package com.pdrw.pdrw.pinskdrevby.rest;

import com.pdrw.pdrw.pinskdrevby.service.ExcelPinskdrevByService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/v1/pinskdrev-by/download")
@RequiredArgsConstructor
@Tag(name = "PinskdrevBy")
public class ExcelPinskdrevByControllerV1 {

    private final ExcelPinskdrevByService excelPinskdrevByService;

    @Operation(summary = "Download excel file")
    @GetMapping("/excel")
    public ResponseEntity<?> getExcel() {
        Path file = excelPinskdrevByService.getExcelFile();

        InputStreamResource resource = null;
        try {
            resource = new InputStreamResource(Files.newInputStream(file));

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFileName().toString());
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
