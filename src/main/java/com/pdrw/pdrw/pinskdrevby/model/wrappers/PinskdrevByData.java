package com.pdrw.pdrw.pinskdrevby.model.wrappers;

import com.pdrw.pdrw.pinskdrevby.model.PinskdrevBy;
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
public class PinskdrevByData {

    private BigDecimal averagePrice;
    private Map<String, BigDecimal> averageByType;
    private Map<String, List<PinskdrevBy>> articleChangeData;
    private Long countAll;

    public PinskdrevByData setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
        return this;
    }

    public PinskdrevByData setCountAll(Long countAll) {
        this.countAll = countAll;
        return this;
    }

    public PinskdrevByData setAverageByType(Map<String, BigDecimal> averageByType) {
        this.averageByType = averageByType;
        return this;
    }

    public PinskdrevByData setArticleChangeData(Map<String, List<PinskdrevBy>> articleChangeData) {
        this.articleChangeData = articleChangeData;
        return this;
    }
}
