package com.example.accounts.service.impl;

import com.example.accounts.dto.Response;
import com.example.accounts.dto.StatementDTO;
import com.example.accounts.entity.Statement;
import com.example.accounts.repository.StatementRepository;
import com.example.accounts.session.JwtTokenUtil;
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
    void getAccountStatement_withAllParameters() {
        Integer accountId = 1;
        Date fromDate = createDate("01/01/2022");
        Date toDate = createDate("12/31/2022");
        BigDecimal fromAmount = BigDecimal.valueOf(1000);
        BigDecimal toAmount = BigDecimal.valueOf(5000);

        List<Statement> statementList = new ArrayList<>();
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
        when(statementRepository.findAllByAccountId(accountId)).thenReturn(statementList);

        Response result = statementService.getAccountStatement(accountId, fromDate, toDate, fromAmount, toAmount);
        List<StatementDTO> expectedFilteredStatement = new ArrayList<>();

        assertEquals(new Response(false, "Statement fetch successful", expectedFilteredStatement), result);
    }

    @Test
    void getAccountStatement_withoutfilter() {
        Integer accountId = 1;
        Date fromDate = null;
        Date toDate = null;
        BigDecimal fromAmount = null;
        BigDecimal toAmount = null;

        List<Statement> statementList = new ArrayList<>();
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