package com.pdrw.pdrw.testdb.service.impl;

import com.pdrw.pdrw.pinskdrev.model.Pinskdrev;
import com.pdrw.pdrw.pinskdrev.service.PinskdrevService;

import java.util.List;

public class TestDBServiceImpl {

    private final PinskdrevService pinskdrevService;


    public TestDBServiceImpl(PinskdrevService pinskdrevService) {
        this.pinskdrevService = pinskdrevService;
    }


    public void fillTestDB() {
        List<Pinskdrev> pinskdrevList = pinskdrevService.findAll();
        for (Pinskdrev pinskdrev : pinskdrevList) {

        }

    }

}
