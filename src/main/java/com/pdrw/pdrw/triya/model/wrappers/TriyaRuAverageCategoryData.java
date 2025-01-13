package com.pdrw.pdrw.triya.model.wrappers;

import com.pdrw.pdrw.triya.model.TriyaRu;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class TriyaRuAverageCategoryData {

    private String nameCategory;
    private BigDecimal averagePrice;
    private TriyaRu minPrice;
    private TriyaRu maxPrice;

    public TriyaRuAverageCategoryData setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
        return this;
    }

    public TriyaRuAverageCategoryData setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
        return this;
    }

    public TriyaRuAverageCategoryData setMinPrice(TriyaRu minPrice) {
        this.minPrice = minPrice;
        return this;
    }

    public TriyaRuAverageCategoryData setMaxPrice(TriyaRu maxPrice) {
        this.maxPrice = maxPrice;
        return this;
    }
}
