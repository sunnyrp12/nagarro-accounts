package com.example.accounts.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StatementControllerTest {

    @Mock
    private StatementService statementService;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private StatementController statementController;


//    @Test
//    void testGetAccountStatement() throws Exception {
//
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        HttpServletResponse response = mock(HttpServletResponse.class);
//        JwtTokenFilter jwtTokenFilter = mock(JwtTokenFilter.class);
//
//        when(request.getParameter("accountId")).thenReturn("1");
//        when(request.getParameter("fromDate")).thenReturn("01/01/2022");
//        when(request.getParameter("toDate")).thenReturn("31/12/2022");
//        when(request.getParameter("fromAmount")).thenReturn("100");
//        when(request.getParameter("toAmount")).thenReturn("500");
//        when(request.getHeader("Authorization")).thenReturn("Bearer TOKEN");
//
//        when(jwtTokenUtil.getUsernameFromToken(anyString())).thenReturn("admin");
//        when(statementService.getAccountStatement(anyInt(), any(), any(), any(), any())).thenReturn(new Response(false, "Statement fetch successful", null));
//
//        Response result = statementController.getAccountStatement(
//            123,
//            DateUtil.parseDate("01/01/2022", "dd/MM/yyyy"),
//            DateUtil.parseDate("31/12/2022", "dd/MM/yyyy"),
//            BigDecimal.valueOf(1000),
//            BigDecimal.valueOf(5000),
//            request,
//            response
//        );
//
//        // Verify the interactions and assertions
//        verify(jwtTokenUtil).getUsernameFromToken("TOKEN");
//        verify(statementService).getAccountStatement(123, DateUtil.parseDate("01/01/2022", "dd/MM/yyyy"), DateUtil.parseDate("31/12/2022", "dd/MM/yyyy"), BigDecimal.valueOf(1000), BigDecimal.valueOf(5000));
//        verify(response, never()).sendError(anyInt(), anyString());
//        assertFalse(result.isErrorStatus());
//        assertEquals("Statement fetch successful", result.getMessage());
//        assertNull(result.getData());
//    }

//    @Test
//    void testGetAccountStatement() throws Exception {
//        // Set up test data
//        Integer accountId = 123;
//        Date fromDate = null;
//        Date toDate = null;
//        BigDecimal fromAmount = null;
//        BigDecimal toAmount = null;
//        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
//        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
//
//        // Mock behavior
//        String token = "testToken";
//        String username = "testUser";
//        when(JwtTokenFilter.extractToken(httpServletRequest)).thenReturn(token);
//        when(jwtTokenUtil.getUsernameFromToken(token)).thenReturn(username);
//        when(jwtTokenUtil.validateJwtToken(token, null)).thenReturn(true);
//
//        // Perform the account statement retrieval
//        Response response = statementController.getAccountStatement(
//            accountId, fromDate, toDate, fromAmount, toAmount,
//            httpServletRequest, httpServletResponse
//        );
//
//        // Verify the interactions and assertions
//        verify(jwtTokenUtil).getUsernameFromToken(token);
//        verify(JwtTokenFilter).extractToken(httpServletRequest);
//        verify(jwtTokenUtil).validateJwtToken(token, null);
//        verify(statementService).getAccountStatement(accountId, fromDate, toDate, fromAmount, toAmount);
//        verify(httpServletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//
//        assertEquals(false, response.isError());
//        assertEquals("Statement fetch successful", response.getMessage());
//        assertEquals(null, response.getData());
//    }

}