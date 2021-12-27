package ormLab.entity;


import ormFramework.annotation.Column;
import ormFramework.annotation.Entity;
import ormFramework.annotation.Id;

@Entity(tableName = "departments")
public class Department {
    @Id
    private int id;

    @Column(name = "name", columnDefinition = "VARCHAR(211)")
    private String name;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getName() {
        return this.name;
    }
}
