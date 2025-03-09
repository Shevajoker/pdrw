package com.pdrw.pdrw.bestmebelru.model.wrappers;

import com.pdrw.pdrw.bestmebelru.model.BestmebelRu;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class BestmebelRuAverageCategoryData {

    private String nameCategory;
    private BigDecimal averagePrice;
    private BestmebelRu minPrice;
    private BestmebelRu maxPrice;

    public BestmebelRuAverageCategoryData setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
        return this;
    }

    public BestmebelRuAverageCategoryData setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
        return this;
    }

    public BestmebelRuAverageCategoryData setMinPrice(BestmebelRu minPrice) {
        this.minPrice = minPrice;
        return this;
    }

    public BestmebelRuAverageCategoryData setMaxPrice(BestmebelRu maxPrice) {
        this.maxPrice = maxPrice;
        return this;
    }
}
