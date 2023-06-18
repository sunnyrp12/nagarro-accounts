package com.example.accounts.controller;

import com.example.accounts.entity.Account;
import com.example.accounts.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


//@RestController
//@RequestMapping("/statements")
public class AccountController {

//    @Autowired
//    private AccountService accountService;
//
//    @GetMapping
//    public List<String> getStatements(
//            @RequestParam String accountId,
//            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
//            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
//            @RequestParam(required = false) Double fromAmount,
//            @RequestParam(required = false) Double toAmount
//    ) {
//        // Perform validation on the request parameters and handle exceptions
//        // Call the statementService.searchStatements() method to retrieve the statements
//        // Hash the account ID before returning the statements
//        // Return the statements
//        List<String> accounts = accountService.getStatement(accountId, fromDate, toDate, fromAmount, toAmount);
//        return accounts;
//    }

}
