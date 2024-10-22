package com.pdrw.pdrw.pinskdrevru.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pinskdrev_ru")
public class PinskdrevRu {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;
    @Column(name = "article")
    private String article;
    @Column(name = "name")
    private String name;
    @Column(name = "image")
    private String image;
    @Column(name = "price_new")
    private BigDecimal priceNew;
    @Column(name = "price_old")
    private BigDecimal priceOld;
    @Column(name = "discount")
    private BigDecimal discount;
    @Column(name = "date_create")
    private Date createDate;
    @Column(name = "date_update")
    private Date dateUpdate;
    @Column(name = "type")
    private String type;
    @Column(name = "length")
    private Integer length;
    @Column(name = "width")
    private Integer width;
    @Column(name = "height")
    private Integer height;
    @Column(name = "weight")
    private Integer weight;
    @Column(name = "volume")
    private Double volume;
    @Column(name = "actual")
    private Boolean actual;
    @Column(name = "link")
    private String link;
}
