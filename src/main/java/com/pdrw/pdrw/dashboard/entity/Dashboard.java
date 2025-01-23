package com.pdrw.pdrw.dashboard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Dashboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String storeName;
    private Integer totalProducts;
    private BigDecimal totalPriceWithoutDiscounts;
    private BigDecimal totalPriceWithDiscounts;
    private BigDecimal averageDiscountPercentage;
    private BigDecimal priceMode;
    private BigDecimal averagePrice;
    private MostPopularCategory mostPopularCategory;
    private LeastPopularCategory leastPopularCategory;
    private MaxDiscountProduct maxDiscountProduct;
    private MinDiscountProduct minDiscountProduct;
    private BigDecimal discountedProductsPercentage;
    private Integer totalDeletedProducts;
    private Integer totalUpdatedProducts;
    private Date lastDateCreate;
    private Date lastDateUpdate;

}
