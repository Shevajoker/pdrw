package com.pdrw.pdrw.triya.model.wrappers;

import com.pdrw.pdrw.triya.model.Triya;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class TriyaAverageCategoryData {

    private String nameCategory;
    private BigDecimal averagePrice;
    private Triya minPrice;
    private Triya maxPrice;

    public TriyaAverageCategoryData setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
        return this;
    }

    public TriyaAverageCategoryData setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
        return this;
    }

    public TriyaAverageCategoryData setMinPrice(Triya minPrice) {
        this.minPrice = minPrice;
        return this;
    }

    public TriyaAverageCategoryData setMaxPrice(Triya maxPrice) {
        this.maxPrice = maxPrice;
        return this;
    }
}
