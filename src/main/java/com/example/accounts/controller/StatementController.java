package com.example.accounts.controller;

import com.example.accounts.dto.Response;
import com.example.accounts.service.StatementService;
import com.example.accounts.session.JwtTokenFilter;
import com.example.accounts.session.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    private static final Logger logger = LoggerFactory.getLogger(StatementController.class);

    @GetMapping
    public Response getAccountStatement(
        @RequestParam Integer accountId,
        @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date fromDate,
        @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date toDate,
        @RequestParam(required = false) BigDecimal fromAmount,
        @RequestParam(required = false) BigDecimal toAmount,
        HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            String token = jwtTokenFilter.extractToken(httpServletRequest);
            String username = jwtTokenUtil.getUsernameFromToken(token);
            logger.info("Received account statement request for accountId: {}", accountId);
            logger.info("Username: {}", username);

            if ((!username.equals("admin")) && (fromDate != null || toDate != null || fromAmount != null || toAmount != null)) {
                logger.warn("Unauthorized access attempt by user: {}", username);
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            }
        } catch (Exception e) {
            logger.error("Error occurred while processing account statement request with Exception -> ", e);
            return new Response(true, "Error", e.getLocalizedMessage());
        }
        logger.info("Account statement generated for accountId: {}", accountId);
        return statementService.getAccountStatement(accountId, fromDate, toDate, fromAmount, toAmount);
    }




}
