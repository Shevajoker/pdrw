package com.pdrw.pdrw.pinskdrevby.rest;

import com.pdrw.pdrw.pinskdrevby.service.ArchivePinskdrevByService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pinskdrev-by/archive")
@RequiredArgsConstructor
@Tag(name = "PinskdrevBy")
public class ArchivePinskdrevByControllerV1 {

    private final ArchivePinskdrevByService archivePinskdrevByService;

    @Operation(summary = "Update archive")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/set")
    public ResponseEntity<?> setData() {
        String file = "classpath:data/archive-pd.json";
        archivePinskdrevByService.setData(file);
//        List<ArchivePinskdrevBy> result = archivePinskdrevByService.getArchivePinskdrevBy();
//        return ResponseEntity.ok(result);
        return ResponseEntity.ok("{\"data\":\"" + file + "\"}");
    }
}
