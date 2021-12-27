package com.example.springdatademo.services;

import com.example.springdatademo.exceptions.InsufficientFundsException;
import com.example.springdatademo.exceptions.NotExistingAccountException;

import java.math.BigDecimal;

public interface AccountService {
    void transferBetweenAccounts(Long from, Long to, BigDecimal amount) throws NotExistingAccountException, InsufficientFundsException;
    void withdrawMoney(BigDecimal amount, Long id) throws NotExistingAccountException, InsufficientFundsException;
    void transferMoney(BigDecimal amount, Long id) throws NotExistingAccountException;
}
