package com.pdrw.pdrw.dashboard.entity.treemap;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DashboardTreeMap {

    private String name;
    private BigDecimal size;
    private Extra extra;
}
