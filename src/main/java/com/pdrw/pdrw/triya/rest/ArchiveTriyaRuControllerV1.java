package com.pdrw.pdrw.triya.rest;

import com.pdrw.pdrw.triya.service.ArchiveTriyaRuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/triya-ru/archive")
@RequiredArgsConstructor
@Tag(name = "TriyaRu")
public class ArchiveTriyaRuControllerV1 {

    private final ArchiveTriyaRuService archiveTriyaRuService;

    @Operation(summary = "Update archive")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/set")
    public ResponseEntity<?> setData() {
        String file = "classpath:data/archive-pd.json";
        archiveTriyaRuService.setData(file);
        return ResponseEntity.ok("{\"data\":\"" + file + "\"}");
    }
}
