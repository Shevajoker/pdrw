package com.pdrw.pdrw.angstrem.model.wrappers;

import com.pdrw.pdrw.angstrem.model.Angstrem;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class AngstremAverageCategoryData {

    private String nameCategory;
    private BigDecimal averagePrice;
    private Angstrem minPrice;
    private Angstrem maxPrice;

    public AngstremAverageCategoryData setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
        return this;
    }

    public AngstremAverageCategoryData setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
        return this;
    }

    public AngstremAverageCategoryData setMinPrice(Angstrem minPrice) {
        this.minPrice = minPrice;
        return this;
    }

    public AngstremAverageCategoryData setMaxPrice(Angstrem maxPrice) {
        this.maxPrice = maxPrice;
        return this;
    }
}
