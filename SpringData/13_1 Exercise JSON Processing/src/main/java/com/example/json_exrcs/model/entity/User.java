package com.example.json_exrcs.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private String firstName;
    private String lastName;
    private Integer age;

    private Set<User> friends;
    private Set<Product> soldProducts;
//    private Set<Product> boughtProducts;

    public User() {
        this.friends = new HashSet<>();
        this.soldProducts = new HashSet<>();
    }

    @Column(name = "first_name", nullable = true)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    @OneToMany(mappedBy = "seller", fetch = FetchType.EAGER)
    public Set<Product> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(Set<Product> soldProducts) {
        this.soldProducts = soldProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(age, user.age) && Objects.equals(friends, user.friends) && Objects.equals(soldProducts, user.soldProducts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age, friends, soldProducts);
    }

    //    @OneToMany(mappedBy = "buyer")
//    public Set<Product> getBoughtProducts() {
//        return boughtProducts;
//    }
//
//    public void setBoughtProducts(Set<Product> boughtProducts) {
//        this.boughtProducts = boughtProducts;
//    }
}
