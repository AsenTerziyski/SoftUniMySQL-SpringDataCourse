package com.example.exercisesdi.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
    private String name;

    // празният конструктор трябва да е наличен винаги:
    public Category() {
    }

    // за да мога да инстанцирам категория от инпут файла в сървис:
    public Category(String name) {
        this.name=name;
    }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    // !!!! shorcut eq => public boolean equals(Object o) {:
    // хеш структурата да държи уникални стойности:
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
