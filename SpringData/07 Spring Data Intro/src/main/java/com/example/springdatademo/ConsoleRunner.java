package com.example.springdatademo;

import com.example.springdatademo.exceptions.InsufficientFundsException;
import com.example.springdatademo.exceptions.UsernameAlreadyExistsException;
import com.example.springdatademo.services.AccountService;
import com.example.springdatademo.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ConsoleRunner implements CommandLineRunner {
    private final UserService userService;
    private final AccountService accountService;

    public ConsoleRunner(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            this.accountService.transferBetweenAccounts(1L, 2L, BigDecimal.valueOf(290));
        } catch (InsufficientFundsException e) {
            e.getClass().getSimpleName();
        }
//        try {
//            this.userService.register("TestUser1", 35, BigDecimal.valueOf(1000));
//        } catch (UsernameAlreadyExistsException e) {
//            System.out.println(e.getClass().getSimpleName());
//        }
//        this.userService.addAccount(new BigDecimal(1000), 1L);
//        this.userService.addAccount(new BigDecimal(1000000), 3L);
//
////        this.userService.register("TestUser2", 35, BigDecimal.valueOf(1500));
////        this.userService.register("TestUser3", 35, BigDecimal.valueOf(1500));
//        try {
//            this.accountService.withdrawMoney(new BigDecimal(700),1L);
//        } catch (InsufficientFundsException e) {
//            System.out.println(e.getClass().getSimpleName());
//        }
//
//        this.accountService.transferMoney(BigDecimal.valueOf(777), 2L);
//
//        try {
//            this.accountService.withdrawMoney(new BigDecimal(10000000),1L);
//        } catch (InsufficientFundsException e) {
//            System.out.println(e.getClass().getSimpleName());
//        }


    }
}
