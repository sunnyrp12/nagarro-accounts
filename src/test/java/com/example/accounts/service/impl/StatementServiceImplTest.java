package com.example.accounts.service.impl;

import com.example.accounts.dto.Response;
import com.example.accounts.dto.StatementDTO;
import com.example.accounts.entity.Account;
import com.example.accounts.entity.Statement;
import com.example.accounts.repository.StatementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatementServiceImplTest {

    @Mock
    private StatementRepository statementRepository;

    @InjectMocks
    private StatementServiceImpl statementService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAccountStatement_withNoRecords() {
        Integer accountId = 1;
        Date fromDate = createDate("01/01/2022");
        Date toDate = createDate("12/31/2022");
        BigDecimal fromAmount = BigDecimal.valueOf(1000);
        BigDecimal toAmount = BigDecimal.valueOf(5000);

        List<Statement> statementList = new ArrayList<>();
        when(statementRepository.findAllByAccountId(accountId)).thenReturn(statementList);

        Response result = statementService.getAccountStatement(accountId, fromDate, toDate, fromAmount, toAmount);
        List<StatementDTO> expectedFilteredStatement = new ArrayList<>();

        assertEquals(new Response(false, "No Records found", new ArrayList<>()), result);
    }

    @Test
    void getAccountStatement_withAllParameters() {
        Integer accountId = 1;
        Date fromDate = createDate("01/01/2022");
        Date toDate = createDate("12/31/2022");
        BigDecimal fromAmount = BigDecimal.valueOf(1000);
        BigDecimal toAmount = BigDecimal.valueOf(5000);

        List<Statement> statementList = new ArrayList<>();
        Account account = new Account();
        account.setAccountNumber("123");
        Statement statement = new Statement();
        statement.setAccount(account);
        statement.setAmount("1000");
        statement.setDatefield("01.01.2020");
        statementList.add(statement);
        when(statementRepository.findAllByAccountId(accountId)).thenReturn(statementList);

        Response result = statementService.getAccountStatement(accountId, fromDate, toDate, fromAmount, toAmount);
        List<StatementDTO> expectedFilteredStatement = new ArrayList<>();

        assertEquals(new Response(false, "Statement fetch successful", expectedFilteredStatement), result);
    }

    @Test
    void getAccountStatement_withDateRange() {
        Integer accountId = 1;
        Date fromDate = createDate("01/01/2022");
        Date toDate = createDate("12/31/2022");
        BigDecimal fromAmount = null;
        BigDecimal toAmount = null;

        List<Statement> statementList = new ArrayList<>();
        Account account = new Account();
        account.setAccountNumber("123");
        Statement statement = new Statement();
        statement.setAccount(account);
        statement.setAmount("1000");
        statement.setDatefield("01.01.2020");
        statementList.add(statement);
        when(statementRepository.findAllByAccountId(accountId)).thenReturn(statementList);

        Response result = statementService.getAccountStatement(accountId, fromDate, toDate, fromAmount, toAmount);
        List<StatementDTO> expectedFilteredStatement = new ArrayList<>();

        assertEquals(new Response(false, "Statement fetch successful", expectedFilteredStatement), result);
    }

    @Test
    void getAccountStatement_withAmountRange() {
        Integer accountId = 1;
        Date fromDate = null;
        Date toDate = null;
        BigDecimal fromAmount = BigDecimal.valueOf(1000);
        BigDecimal toAmount = BigDecimal.valueOf(5000);

        List<Statement> statementList = new ArrayList<>();
        Account account = new Account();
        account.setAccountNumber("123");
        Statement statement = new Statement();
        statement.setAccount(account);
        statement.setAmount("1000");
        statement.setDatefield("01.01.2020");
        statementList.add(statement);
        when(statementRepository.findAllByAccountId(accountId)).thenReturn(statementList);

        Response result = statementService.getAccountStatement(accountId, fromDate, toDate, fromAmount, toAmount);
        List<StatementDTO> expectedFilteredStatement = new ArrayList<>();

        assertEquals(new Response(false, "Statement fetch successful", expectedFilteredStatement), result);
    }

    @Test
    void getAccountStatement_withoutFilter() {
        Integer accountId = 1;
        Date fromDate = null;
        Date toDate = null;
        BigDecimal fromAmount = null;
        BigDecimal toAmount = null;

        List<Statement> statementList = new ArrayList<>();
        Account account = new Account();
        account.setAccountNumber("123");
        Statement statement = new Statement();
        statement.setAccount(account);
        statement.setAmount("1000");
        statement.setDatefield("01.01.2020");
        statementList.add(statement);
        when(statementRepository.findAllByAccountId(accountId)).thenReturn(statementList);

        Response result = statementService.getAccountStatement(accountId, fromDate, toDate, fromAmount, toAmount);
        List<StatementDTO> expectedFilteredStatement = new ArrayList<>();

        assertEquals(new Response(false, "Statement fetch successful", expectedFilteredStatement), result);
    }

    @Test
    void getAccountStatement_withException() {
        Integer accountId = 1;
        Date fromDate = createDate("01/01/2022");
        Date toDate = createDate("12/31/2022");
        BigDecimal fromAmount = BigDecimal.valueOf(1000);
        BigDecimal toAmount = BigDecimal.valueOf(5000);

        List<Statement> statementList = new ArrayList<>();
        Account account = new Account();
        Statement statement = new Statement();
        statement.setAccount(account);
        statement.setAmount("1000");
        statement.setDatefield("01.01.2020");
        statementList.add(statement);
        when(statementRepository.findAllByAccountId(accountId)).thenReturn(statementList);

        Response result = statementService.getAccountStatement(accountId, fromDate, toDate, fromAmount, toAmount);
        List<StatementDTO> expectedFilteredStatement = new ArrayList<>();

        assertEquals(new Response(true, "Statement fetch Failed", null), result);
    }

    @Test
    void getAccountStatement_withParseException() {
        Integer accountId = 1;
        Date fromDate = createDate("01/01/2022");
        Date toDate = createDate("12/31/2022");
        BigDecimal fromAmount = BigDecimal.valueOf(1000);
        BigDecimal toAmount = BigDecimal.valueOf(5000);

        List<Statement> statementList = new ArrayList<>();
        Account account = new Account();
        account.setAccountNumber("123");
        Statement statement = new Statement();
        statement.setAccount(account);
        statement.setAmount("1000");
        statement.setDatefield("01012020");
        statementList.add(statement);
        when(statementRepository.findAllByAccountId(accountId)).thenReturn(statementList);

        Response result = statementService.getAccountStatement(accountId, fromDate, toDate, fromAmount, toAmount);
        List<StatementDTO> expectedFilteredStatement = new ArrayList<>();

        assertEquals(new Response(false, "Statement fetch successful", expectedFilteredStatement), result);
    }

    private Date createDate(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            return dateFormat.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}