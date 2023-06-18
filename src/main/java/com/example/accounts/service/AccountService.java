package com.example.accounts.service;

import com.example.accounts.entity.Account;

import java.util.Date;
import java.util.List;

public interface AccountService {
    List<String> getStatement(String accountId, Date fromDate, Date toDate, Double fromAmount, Double toAmount);
}
