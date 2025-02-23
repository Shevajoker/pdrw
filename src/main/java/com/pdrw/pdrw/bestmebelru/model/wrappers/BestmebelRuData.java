package com.pdrw.pdrw.bestmebelru.model.wrappers;

import com.pdrw.pdrw.bestmebelru.model.BestmebelRu;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class BestmebelRuData {

    private BigDecimal averagePrice;
    private Map<String, BigDecimal> averageByType;
    private Map<String, List<BestmebelRu>> articleChangeData;
    private Long countAll;

    public BestmebelRuData setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
        return this;
    }

    public BestmebelRuData setCountAll(Long countAll) {
        this.countAll = countAll;
        return this;
    }

    public BestmebelRuData setAverageByType(Map<String, BigDecimal> averageByType) {
        this.averageByType = averageByType;
        return this;
    }

    public BestmebelRuData setArticleChangeData(Map<String, List<BestmebelRu>> articleChangeData) {
        this.articleChangeData = articleChangeData;
        return this;
    }
}
