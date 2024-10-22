package com.pdrw.pdrw.pinskdrevru.model.wrappers;

import com.pdrw.pdrw.pinskdrevru.model.PinskdrevRu;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class PinskdrevRuAverageCategoryData {

    private String nameCategory;
    private BigDecimal averagePrice;
    private PinskdrevRu minPrice;
    private PinskdrevRu maxPrice;

    public PinskdrevRuAverageCategoryData setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
        return this;
    }

    public PinskdrevRuAverageCategoryData setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
        return this;
    }

    public PinskdrevRuAverageCategoryData setMinPrice(PinskdrevRu minPrice) {
        this.minPrice = minPrice;
        return this;
    }

    public PinskdrevRuAverageCategoryData setMaxPrice(PinskdrevRu maxPrice) {
        this.maxPrice = maxPrice;
        return this;
    }
}
