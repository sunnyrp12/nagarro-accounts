package com.example.accounts.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class StatementDTO {

    private String accountType;

    private String accountNumber;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date datefield;

    private BigDecimal amount;



}
