package com.pdrw.pdrw.hoff.service;

import com.pdrw.pdrw.hoff.model.HoffItem;

import java.io.IOException;
import java.util.List;

public interface GetHoffDataService {

    int setData(String data) throws IOException;
    List<HoffItem> getData(Integer limit);

}
