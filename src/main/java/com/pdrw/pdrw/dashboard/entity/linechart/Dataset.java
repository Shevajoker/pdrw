package com.pdrw.pdrw.dashboard.entity.linechart;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Dataset {

    String label;
    List<Integer> data;

}
