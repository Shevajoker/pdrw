package com.pdrw.pdrw.triya.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArchiveTriyaRuDto {

    private ArchiveTriyaRu archiveTriyaRu;
    private List<String> urls;
}
