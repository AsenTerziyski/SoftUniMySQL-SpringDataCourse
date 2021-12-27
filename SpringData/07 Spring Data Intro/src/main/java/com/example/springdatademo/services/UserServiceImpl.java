package com.example.springdatademo.services;

import com.example.springdatademo.exceptions.UserNotFoundException;
import com.example.springdatademo.exceptions.UsernameAlreadyExistsException;
import com.example.springdatademo.models.Account;
import com.example.springdatademo.models.User;
import com.example.springdatademo.repositories.AccountRepository;
import com.example.springdatademo.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

//за да може да го инстанцира Спринг тряба да му дадем анотация @Service:
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public UserServiceImpl(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void register(String username, int age, BigDecimal initialAmount) throws UsernameAlreadyExistsException {
        if (this.userRepository.existsByUsername(username)) {
            throw new UsernameAlreadyExistsException();
        }
        var user = new User();
        user.setUsername(username);
        user.setAge(age);
        this.userRepository.save(user);

        this.saveAccount(initialAmount, user);

    }

    private void saveAccount(BigDecimal initialAmount, User user) {
        var account = new Account();
        account.setBalance(initialAmount);
        account.setUser(user);
        this.accountRepository.save(account);
    }

    @Override
    public void addAccount(BigDecimal amount, long id) throws UserNotFoundException {
        User user = this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        this.saveAccount(amount, user);
    }
}
