package com.pdrw.pdrw.pinskdrev.model.wrappers;

import com.pdrw.pdrw.pinskdrev.model.Pinskdrev;
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
public class PinskdrevData {

    private BigDecimal averagePrice;
    private Map<String, BigDecimal> averageByType;
    private Map<String, List<Pinskdrev>> articleChangeData;
    private Long countAll;

    public PinskdrevData setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
        return this;
    }

    public PinskdrevData setCountAll(Long countAll) {
        this.countAll = countAll;
        return this;
    }

    public PinskdrevData setAverageByType(Map<String, BigDecimal> averageByType) {
        this.averageByType = averageByType;
        return this;
    }

    public PinskdrevData setArticleChangeData(Map<String, List<Pinskdrev>> articleChangeData) {
        this.articleChangeData = articleChangeData;
        return this;
    }
}
