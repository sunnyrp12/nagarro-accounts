package com.example.accounts.controller;

import com.example.accounts.dto.Response;
import com.example.accounts.service.StatementService;
import com.example.accounts.session.JwtTokenFilter;
import com.example.accounts.session.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequestMapping("/statements")
public class StatementController {

    @Autowired
    private StatementService statementService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping
    public Response getAccountStatement(
            @RequestParam Integer accountId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date fromDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date toDate,
            @RequestParam(required = false) BigDecimal fromAmount,
            @RequestParam(required = false) BigDecimal toAmount,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            String username = jwtTokenUtil.getUsernameFromToken(JwtTokenFilter.extractToken(httpServletRequest));
            if ((!username.equals("admin")) && (fromDate != null || toDate != null || fromAmount != null || toAmount != null)) {
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            }
            validateDates(fromDate, toDate);
            validateAmounts(fromAmount, toAmount);
        } catch (Exception e) {
            return new Response(true, "Error", e.getLocalizedMessage());
        }
        return statementService.getAccountStatement(accountId, fromDate, toDate, fromAmount, toAmount);

    }

    private void validateDates(Date fromDate, Date toDate) {
        if ((fromDate == null && toDate != null) || (fromDate != null && toDate == null)) {
            throw new IllegalArgumentException("Both fromDate and toDate must be present or both should be null");
        }
    }

    private void validateAmounts(BigDecimal fromAmount, BigDecimal toAmount) {
        if ((fromAmount == null && toAmount != null) || (fromAmount != null && toAmount == null)) {
            throw new IllegalArgumentException("Both fromAmount and toAmount must be present or both should be null");
        }
    }

}
