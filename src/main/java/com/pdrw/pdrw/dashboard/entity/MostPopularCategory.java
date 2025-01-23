package com.pdrw.pdrw.dashboard.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class MostPopularCategory {

    private String mostPopularCategoryName;
    private Integer mostPopularCategoryCount;
}
