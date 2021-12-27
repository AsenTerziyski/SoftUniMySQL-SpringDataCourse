package com.example.springdatademo.models;


import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "users")
public class User extends BaseEntity {


    @Column(name = "username", unique = true)
    private String username;
    private int age;

    @OneToMany(mappedBy = "user")
    private List<Account> accounts;



    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
