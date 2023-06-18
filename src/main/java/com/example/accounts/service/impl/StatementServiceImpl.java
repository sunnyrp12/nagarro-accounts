package com.example.accounts.service.impl;

import com.example.accounts.dto.Response;
import com.example.accounts.dto.StatementDTO;
import com.example.accounts.entity.Statement;
import com.example.accounts.repository.StatementRepository;
import com.example.accounts.service.StatementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatementServiceImpl implements StatementService {


    @Autowired
    private StatementRepository statementRepository;



    @Override
    public Response getAccountStatement(Integer accountId, Date fromDate, Date toDate, BigDecimal fromAmount, BigDecimal toAmount) {
        List<Statement> statementList = statementRepository.findAllByAccountId(accountId);
        List<StatementDTO> statementDTOList = statementEntityToDTO(statementList);
        List<StatementDTO> filteredStatement = filterStatement(statementDTOList, fromDate, toDate, fromAmount, toAmount);
        return new Response(false, "Statement fetch successful", filteredStatement);
    }

    private List<StatementDTO> filterStatement(List<StatementDTO> statementDTOList, Date fromDate, Date toDate, BigDecimal fromAmount, BigDecimal toAmount) {
        List<StatementDTO> filteredStatement = new ArrayList<>();
        if (fromDate != null && toDate != null && fromAmount != null && toAmount != null) {
            filteredStatement = statementDTOList.stream().filter(statementDTO ->
                    statementDTO.getDatefield().after(fromDate) && statementDTO.getDatefield().before(toDate)
                            && statementDTO.getAmount().compareTo(fromAmount) > 0 && statementDTO.getAmount().compareTo(toAmount) < 0
            ).collect(Collectors.toList());
        } else if (fromDate != null && toDate != null) {
            filteredStatement = statementDTOList.stream().filter(statementDTO ->
                    statementDTO.getDatefield().after(fromDate) && statementDTO.getDatefield().before(toDate)
            ).collect(Collectors.toList());
        } else if (fromAmount != null && toAmount != null) {
            filteredStatement = statementDTOList.stream().filter(statementDTO ->
                    statementDTO.getAmount().compareTo(fromAmount) > 0 && statementDTO.getAmount().compareTo(toAmount) < 0
            ).collect(Collectors.toList());
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -3);
            Date threeMonthsAgo = calendar.getTime();
            filteredStatement = statementDTOList.stream().filter(statementDTO ->
                    statementDTO.getDatefield().compareTo(threeMonthsAgo) > 0
            ).collect(Collectors.toList());
        }
        return filteredStatement;
    }

    private List<StatementDTO> statementEntityToDTO(List<Statement> statementList) {
        List<StatementDTO> statementDTOList = new ArrayList<>();
        statementList.forEach(statement -> {
            StatementDTO statementDTO = new StatementDTO();
            try {
                statementDTO.setAccountType(statement.getAccount().getAccountType());
                statementDTO.setAccountNumber(hashString(statement.getAccount().getAccountNumber()));
                statementDTO.setAmount(new BigDecimal(statement.getAmount()));
                statementDTO.setDatefield(new SimpleDateFormat("dd.MM.yyyy").parse(statement.getDatefield()));
                statementDTOList.add(statementDTO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return statementDTOList;
    }
    public static String hashString(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

}
