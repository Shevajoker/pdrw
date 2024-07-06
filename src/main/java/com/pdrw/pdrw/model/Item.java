package com.pdrw.pdrw.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "item")
public class Item implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private String id;
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


//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "price_id", referencedColumnName = "id")
//    private Price price;

    public Item setId(String id) {
        this.id = id;
        return this;
    }

    public Item setName(String name) {
        this.name = name;
        return this;
    }

    public Item setImage(String image) {
        this.image = image;
        return this;
    }

    public Item setNewPrice(Integer newPrice) {
        this.newPrice = newPrice;
        return this;
    }

    public Item setOldPrice(Integer oldPrice) {
        this.oldPrice = oldPrice;
        return this;
    }

    public Item setDiscount(Integer discount) {
        this.discount = discount;
        return this;
    }
}
