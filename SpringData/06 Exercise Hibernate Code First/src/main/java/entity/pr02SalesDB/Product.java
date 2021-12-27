package entity.pr02SalesDB;

import entity.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    private String name;
    private Integer quantity;
    private BigDecimal price;

    private Set<Sale> sales;

    public Product() {
        this.sales = new HashSet<>();
    }

    @Column(name = "name", unique = true)
    public String getName() {
        return name;
    }

    @Column
    public Integer getQuantity() {
        return quantity;
    }

    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    public Set<Sale> getSales() {
        return sales;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }
}
