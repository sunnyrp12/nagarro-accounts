package com.example.accounts.service;

import com.example.accounts.dto.Response;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;

public interface StatementService {
    Response getAccountStatement(Integer accountId, Date fromDate, Date toDate, BigDecimal fromAmount, BigDecimal toAmount);
}
