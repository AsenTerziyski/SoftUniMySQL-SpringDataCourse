package ormLab.entity;


import ormFramework.annotation.Column;
import ormFramework.annotation.Entity;
import ormFramework.annotation.Id;

@Entity(tableName = "addresses")
public class Address {

    @Id
    private int id;
    @Column(name = "street", columnDefinition = "VARCHAR(100) NOT NULL")
    private String street;

    @Column(name = "Street_number", columnDefinition = "VARCHAR(200) DEFAULT '1000'")
    private String streetNumber;

    @Column(name = "people_count", columnDefinition = "INT(11)")
    private int peopleCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public int getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(int peopleCount) {
        this.peopleCount = peopleCount;
    }
}
