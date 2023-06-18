package com.example.accounts.service.impl;

import com.example.accounts.entity.Account;
import com.example.accounts.repository.AccountRepository;
import com.example.accounts.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public List<String> getStatement(String accountId, Date fromDate, Date toDate, Double fromAmount, Double toAmount) {
        // Implement the logic to search statements based on the specified criteria
        List<Account> accounts = accountRepository.findAll();
        List<String> accNumbers =  accounts.stream().map(account -> account.getAccountNumber()).collect(Collectors.toList());
        return accNumbers;
    }
}
