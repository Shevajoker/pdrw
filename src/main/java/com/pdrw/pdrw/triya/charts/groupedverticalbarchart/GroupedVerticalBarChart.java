package com.pdrw.pdrw.triya.charts.groupedverticalbarchart;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class GroupedVerticalBarChart {

    private String name;
    private List<Series> series = new ArrayList<>();
}
