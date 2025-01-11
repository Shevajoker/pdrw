package com.pdrw.pdrw.pinskdrevby.service;

import com.pdrw.pdrw.pinskdrevby.model.ArchivePinskdrevBy;

import java.util.List;

public interface ArchivePinskdrevByService {

    void setData(String data);

    List<ArchivePinskdrevBy> getArchivePinskdrevBy();

}
