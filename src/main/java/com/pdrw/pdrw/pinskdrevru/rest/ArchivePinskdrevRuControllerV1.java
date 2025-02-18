package com.pdrw.pdrw.pinskdrevru.rest;

import com.pdrw.pdrw.pinskdrevru.service.ArchivePinskdrevRuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pinskdrev-ru/archive")
@RequiredArgsConstructor
@Tag(name = "PinskdrevRu")
public class ArchivePinskdrevRuControllerV1 {

    private final ArchivePinskdrevRuService archivePinskdrevRuService;

    @Operation(summary = "Update archive")
    @PostMapping("/set")
    public ResponseEntity<?> setData() {
        String file = "classpath:data/archive-pd.json";
        archivePinskdrevRuService.setData(file);
//        List<ArchivePinskdrevRu> result = archivePinskdrevRuService.getArchivePinskdrevRu();
//        return ResponseEntity.ok(result);
        return ResponseEntity.ok("{\"data\":\"" + file + "\"}");
    }
}
