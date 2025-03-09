package com.pdrw.pdrw.bestmebelru.rest;

import com.pdrw.pdrw.bestmebelru.service.CSVBestmebelRuService;
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
@RequestMapping("/api/v1/bestmebel-ru/download")
@RequiredArgsConstructor
@Tag(name = "BestmebelRu")
public class CSVBestmebelRuControllerV1 {

    private final CSVBestmebelRuService csvBestmebelRuService;

    @Operation(summary = "Download csv file")
    @GetMapping("/csv")
    public ResponseEntity<?> getExcel() {
        Path file = csvBestmebelRuService.getCSVPath();

        InputStreamResource resource = null;
        try {
            resource = new InputStreamResource(Files.newInputStream(file));

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFileName().toString());
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
