package com.pdrw.pdrw.pinskdrevby.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArchivePinskdrevByDto {

    private ArchivePinskdrevBy archivePinskdrevBy;
    private List<String> urls;
}
