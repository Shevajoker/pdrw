package com.pdrw.pdrw.hoff.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hoff_item")
public class HoffItem implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @UuidGenerator
    private UUID id;
    @Column(name = "article")
    private String article;
    @Column(name = "date-update")
    private Date dateUpdate;
    @Column(name = "name")
    private String name;
    @Column(name = "image")
    private String image;
    @Column(name = "price_new")
    private Integer newPrice;
    @Column(name = "price_old")
    private Integer oldPrice;
    @Column(name = "discount")
    private Integer discount;

    public HoffItem setId(UUID id) {
        this.id = id;
        return this;
    }

    public HoffItem setName(String name) {
        this.name = name;
        return this;
    }

    public HoffItem setImage(String image) {
        this.image = image;
        return this;
    }

    public HoffItem setNewPrice(Integer newPrice) {
        this.newPrice = newPrice;
        return this;
    }

    public HoffItem setOldPrice(Integer oldPrice) {
        this.oldPrice = oldPrice;
        return this;
    }

    public HoffItem setDiscount(Integer discount) {
        this.discount = discount;
        return this;
    }

    public HoffItem setArticle(String article) {
        this.article = article;
        return this;
    }

    public HoffItem setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
        return this;
    }
}
