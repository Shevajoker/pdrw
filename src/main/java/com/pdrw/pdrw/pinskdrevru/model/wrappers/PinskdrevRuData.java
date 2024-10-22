package com.pdrw.pdrw.pinskdrevru.model.wrappers;

import com.pdrw.pdrw.pinskdrevru.model.PinskdrevRu;
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
public class PinskdrevRuData {

    private BigDecimal averagePrice;
    private Map<String, BigDecimal> averageByType;
    private Map<String, List<PinskdrevRu>> articleChangeData;
    private Long countAll;

    public PinskdrevRuData setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
        return this;
    }

    public PinskdrevRuData setCountAll(Long countAll) {
        this.countAll = countAll;
        return this;
    }

    public PinskdrevRuData setAverageByType(Map<String, BigDecimal> averageByType) {
        this.averageByType = averageByType;
        return this;
    }

    public PinskdrevRuData setArticleChangeData(Map<String, List<PinskdrevRu>> articleChangeData) {
        this.articleChangeData = articleChangeData;
        return this;
    }
}
