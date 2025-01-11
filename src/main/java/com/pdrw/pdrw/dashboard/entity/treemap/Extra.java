package com.pdrw.pdrw.dashboard.entity.treemap;

import lombok.Data;

@Data
public class Extra {
    private String label;

    public Extra setLabel(String label) {
        this.label = label;
        return this;
    }
}
