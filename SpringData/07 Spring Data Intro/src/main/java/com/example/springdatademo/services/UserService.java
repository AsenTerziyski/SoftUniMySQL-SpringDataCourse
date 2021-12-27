package com.example.springdatademo.services;

import com.example.springdatademo.exceptions.UserNotFoundException;
import com.example.springdatademo.exceptions.UsernameAlreadyExistsException;

import java.math.BigDecimal;

public interface UserService {
    void register(String username, int age,  BigDecimal initialAmount) throws UsernameAlreadyExistsException;
    void addAccount(BigDecimal amount, long id) throws UserNotFoundException;

}
