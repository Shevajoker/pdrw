package com.pdrw.pdrw.bestmebelru.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bestmebel_ru")
public class BestmebelRu {

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
    @Column(name = "actual")
    private Boolean actual;
    @Column(name = "link")
    private String link;
}
