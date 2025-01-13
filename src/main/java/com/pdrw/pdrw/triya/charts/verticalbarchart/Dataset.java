package com.pdrw.pdrw.triya.charts.verticalbarchart;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class Dataset {

    private String label;
    private List<BigDecimal> data = new ArrayList<>();
    private String backgroundColor = null;
}
