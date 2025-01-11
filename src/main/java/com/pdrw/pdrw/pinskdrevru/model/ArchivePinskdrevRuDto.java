package com.pdrw.pdrw.pinskdrevru.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArchivePinskdrevRuDto {

    private ArchivePinskdrevRu archivePinskdrevRu;
    private List<String> urls;
}
