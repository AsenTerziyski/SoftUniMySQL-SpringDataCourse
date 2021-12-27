package com.example.xml_xrcs.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    private String name;
    private BigDecimal price;
    private com.example.xml_xrcs.model.entity.User seller;
    private com.example.xml_xrcs.model.entity.User buyer;
    private Set<com.example.xml_xrcs.model.entity.Category> categories;

    public Product() {
    }

    @ManyToMany
    public Set<com.example.xml_xrcs.model.entity.Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<com.example.xml_xrcs.model.entity.Category> categories) {
        this.categories = categories;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @ManyToOne
    public com.example.xml_xrcs.model.entity.User getSeller() {
        return seller;
    }

    public void setSeller(com.example.xml_xrcs.model.entity.User seller) {
        this.seller = seller;
    }

    @ManyToOne
    public com.example.xml_xrcs.model.entity.User getBuyer() {
        return buyer;
    }

    public void setBuyer(com.example.xml_xrcs.model.entity.User buyer) {
        this.buyer = buyer;
    }
}
