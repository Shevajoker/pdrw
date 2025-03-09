package com.pdrw.pdrw.bestmebelru.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArchiveBestmebelRuDto {

    private ArchiveBestmebelRu archiveBestmebelRu;
    private List<String> urls;
}
