package com.pdrw.pdrw.pinskdrevby.model.wrappers;

import com.pdrw.pdrw.pinskdrevby.model.PinskdrevBy;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class PinskdrevByAverageCategoryData {

    private String nameCategory;
    private BigDecimal averagePrice;
    private PinskdrevBy minPrice;
    private PinskdrevBy maxPrice;

    public PinskdrevByAverageCategoryData setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
        return this;
    }

    public PinskdrevByAverageCategoryData setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
        return this;
    }

    public PinskdrevByAverageCategoryData setMinPrice(PinskdrevBy minPrice) {
        this.minPrice = minPrice;
        return this;
    }

    public PinskdrevByAverageCategoryData setMaxPrice(PinskdrevBy maxPrice) {
        this.maxPrice = maxPrice;
        return this;
    }
}
