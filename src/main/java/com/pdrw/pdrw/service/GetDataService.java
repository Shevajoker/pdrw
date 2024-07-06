package com.pdrw.pdrw.service;

import com.pdrw.pdrw.model.Item;

import java.io.IOException;
import java.util.List;

public interface GetDataService {

    int setData(String data) throws IOException;
    List<Item> getData(Integer limit);

}
