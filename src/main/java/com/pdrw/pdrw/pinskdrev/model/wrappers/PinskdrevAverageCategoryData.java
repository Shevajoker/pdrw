package com.pdrw.pdrw.pinskdrev.model.wrappers;

import com.pdrw.pdrw.pinskdrev.model.Pinskdrev;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class PinskdrevAverageCategoryData {

    private String nameCategory;
    private BigDecimal averagePrice;
    private Pinskdrev minPrice;
    private Pinskdrev maxPrice;

    public PinskdrevAverageCategoryData setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
        return this;
    }

    public PinskdrevAverageCategoryData setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
        return this;
    }

    public PinskdrevAverageCategoryData setMinPrice(Pinskdrev minPrice) {
        this.minPrice = minPrice;
        return this;
    }

    public PinskdrevAverageCategoryData setMaxPrice(Pinskdrev maxPrice) {
        this.maxPrice = maxPrice;
        return this;
    }
}
