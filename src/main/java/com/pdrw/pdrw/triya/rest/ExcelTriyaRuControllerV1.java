package com.pdrw.pdrw.triya.rest;

import com.pdrw.pdrw.triya.service.ExcelTriyaRuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/v1/triya-ru/download")
@RequiredArgsConstructor
@Tag(name = "TriyaRu")
public class ExcelTriyaRuControllerV1 {

    private final ExcelTriyaRuService excelService;

    @Operation(summary = "Download excel file")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/excel")
    public ResponseEntity<?> getExcel() {
        Path file = excelService.getExcelFile();

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
