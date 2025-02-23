package com.pdrw.pdrw.bestmebelru.rest;

import com.pdrw.pdrw.bestmebelru.service.ArchiveBestmebelRuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bestmebel-ru/archive")
@RequiredArgsConstructor
@Tag(name = "BestmebelRu")
public class ArchiveBestmebelRuControllerV1 {

    private final ArchiveBestmebelRuService archiveBestmebelRuService;

    @Operation(summary = "Update archive")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/set")
    public ResponseEntity<?> setData() {
        String file = "classpath:data/archive-pd.json";
        archiveBestmebelRuService.setData(file);
        return ResponseEntity.ok("{\"data\":\"" + file + "\"}");
    }
}
