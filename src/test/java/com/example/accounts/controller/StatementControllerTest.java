package com.example.accounts.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.accounts.dto.Response;
import com.example.accounts.service.StatementService;
import com.example.accounts.session.JwtTokenFilter;
import com.example.accounts.session.JwtTokenUtil;
import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

@ExtendWith(MockitoExtension.class)
class StatementControllerTest {

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private JwtTokenFilter jwtTokenFilter;

    @Mock
    private HttpServletRequest request;
    @Mock
    private StatementService statementService;

    @InjectMocks
    private StatementController statementController;

    private MockHttpServletRequest httpServletRequest;
    private MockHttpServletResponse httpServletResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        httpServletRequest = new MockHttpServletRequest();
        httpServletResponse = new MockHttpServletResponse();
    }

    @Test
    void getAccountStatement_withValidInput_successResponse() throws Exception {

        Integer accountId = 123;
        Date fromDate = new Date();
        Date toDate = new Date();
        BigDecimal fromAmount = new BigDecimal("100.00");
        BigDecimal toAmount = new BigDecimal("200.00");

        when(jwtTokenUtil.getUsernameFromToken(anyString())).thenReturn("admin");
        when(jwtTokenFilter.extractToken(any())).thenReturn("test");

        when(statementService.getAccountStatement(anyInt(), any(Date.class), any(Date.class), any(BigDecimal.class), any(BigDecimal.class)))
                .thenReturn(new Response(false, "Success", "Statement"));

        Response response = statementController.getAccountStatement(
                accountId, fromDate, toDate, fromAmount, toAmount, httpServletRequest, httpServletResponse);

        assertFalse(response.isErrorStatus());
        assertEquals("Success", response.getMessage());
        assertNotNull(response.getData());
    }

    @Test
    void getAccountStatement_withException() throws Exception {

        Integer accountId = 123;
        Date fromDate = new Date();
        Date toDate = new Date();
        BigDecimal fromAmount = new BigDecimal("100.00");
        BigDecimal toAmount = new BigDecimal("200.00");

        when(jwtTokenUtil.getUsernameFromToken(anyString())).thenReturn("admin");
        when(jwtTokenFilter.extractToken(any())).thenReturn(null);

        when(statementService.getAccountStatement(anyInt(), any(Date.class), any(Date.class), any(BigDecimal.class), any(BigDecimal.class)))
            .thenReturn(new Response(false, "Success", "Statement"));

        Response response = statementController.getAccountStatement(
            accountId, fromDate, toDate, fromAmount, toAmount, httpServletRequest, httpServletResponse);

        assertTrue(response.isErrorStatus());
        assertEquals("Error", response.getMessage());
    }

    @Test
    void getAccountStatement_withNonAdminUserAndParameters_unauthorizedResponse() throws Exception {
        Integer accountId = 123;
        Date fromDate = new Date();
        Date toDate = new Date();
        BigDecimal fromAmount = new BigDecimal("100.00");
        BigDecimal toAmount = new BigDecimal("200.00");

        when(jwtTokenUtil.getUsernameFromToken(anyString())).thenReturn("user");
        when(jwtTokenFilter.extractToken(any())).thenReturn("test");

        when(statementService.getAccountStatement(anyInt(), any(Date.class), any(Date.class), any(BigDecimal.class), any(BigDecimal.class)))
                .thenReturn(new Response(false, "Success", "Statement"));

        Response response = statementController.getAccountStatement(
                accountId, fromDate, toDate, fromAmount, toAmount, httpServletRequest, httpServletResponse);

        assertFalse(response.isErrorStatus());
        assertEquals("Success", response.getMessage());
        assertNotNull(response.getData());
    }




}