package com.pdrw.pdrw.triya.model.wrappers;

import com.pdrw.pdrw.triya.model.TriyaRu;
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
public class TriyaRuData {

    private BigDecimal averagePrice;
    private Map<String, BigDecimal> averageByType;
    private Map<String, List<TriyaRu>> articleChangeData;
    private Long countAll;

    public TriyaRuData setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
        return this;
    }

    public TriyaRuData setCountAll(Long countAll) {
        this.countAll = countAll;
        return this;
    }

    public TriyaRuData setAverageByType(Map<String, BigDecimal> averageByType) {
        this.averageByType = averageByType;
        return this;
    }

    public TriyaRuData setArticleChangeData(Map<String, List<TriyaRu>> articleChangeData) {
        this.articleChangeData = articleChangeData;
        return this;
    }
}
