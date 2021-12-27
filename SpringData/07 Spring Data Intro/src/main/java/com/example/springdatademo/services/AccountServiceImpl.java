package com.example.springdatademo.services;

import com.example.springdatademo.exceptions.InsufficientFundsException;
import com.example.springdatademo.exceptions.NotExistingAccountException;
import com.example.springdatademo.models.Account;
import com.example.springdatademo.repositories.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    @Transactional
    public void transferBetweenAccounts(Long fromId, Long toId, BigDecimal amount) throws NotExistingAccountException, InsufficientFundsException {
//        var fromAccount = this.getAccount(fromId);
//        throwIfInsufficientAmount(amount,fromAccount);
//        withdrawMoney(amount, fromAccount.getId());
//        var toAccount = this.getAccount(toId);
        this.withdrawMoney(amount,fromId);
        this.transferMoney(amount, toId);

    }

    @Override
    public void withdrawMoney(BigDecimal amount, Long id) throws NotExistingAccountException, InsufficientFundsException {
        Account account = this.getAccount(id);
        throwIfInsufficientAmount(amount, account);
        account.setBalance(account.getBalance().subtract(amount));
        this.accountRepository.save(account);
    }

    private void throwIfInsufficientAmount(BigDecimal amount, Account account) throws InsufficientFundsException {
        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException();
        }
    }

    private Account getAccount(Long id) throws NotExistingAccountException {
        return this.accountRepository.findById(id).orElseThrow(NotExistingAccountException::new);
    }

    @Override
    public void transferMoney(BigDecimal amount, Long id) throws NotExistingAccountException {
        Account account = this.getAccount(id);
        account.setBalance(account.getBalance().add(amount));
        this.accountRepository.save(account);
    }
}
