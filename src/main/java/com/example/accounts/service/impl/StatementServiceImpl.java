package com.example.accounts.service.impl;

import com.example.accounts.dto.Response;
import com.example.accounts.dto.StatementDTO;
import com.example.accounts.entity.Statement;
import com.example.accounts.repository.StatementRepository;
import com.example.accounts.service.StatementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
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

    private static final Logger logger = LoggerFactory.getLogger(StatementServiceImpl.class);


    @Override
    public Response getAccountStatement(Integer accountId, Date fromDate, Date toDate, BigDecimal fromAmount, BigDecimal toAmount) {
        logger.info("Fetching account statement for account ID: {}", accountId);
        List<StatementDTO> filteredStatement;
        try {

            validateDates(fromDate, toDate);
            validateAmounts(fromAmount, toAmount);

            List<Statement> statementList = statementRepository.findAllByAccountId(accountId);
            if (statementList == null || statementList.isEmpty()) {
                logger.info("No Records found");
                return new Response(false, "No Records found", new ArrayList<>());
            }
            List<StatementDTO> statementDTOList = statementEntityToDTO(statementList);
            filteredStatement = filterStatement(statementDTOList, fromDate, toDate, fromAmount, toAmount);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            return new Response(true, "Statement fetch Failed", null);
        }
        logger.info("Account statement fetch successful");
        return new Response(false, "Statement fetch successful", filteredStatement);
    }


    private List<StatementDTO> filterStatement(List<StatementDTO> statementDTOList, Date fromDate, Date toDate, BigDecimal fromAmount, BigDecimal toAmount) {
        logger.info("Enter into Filter statement");
        List<StatementDTO> filteredStatement;

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
        logger.info("Exit from Filter statement");
        return filteredStatement;
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

    private List<StatementDTO> statementEntityToDTO(List<Statement> statementList) {
        List<StatementDTO> statementDTOList = new ArrayList<>();
        statementList.forEach(statement -> {
            StatementDTO statementDTO = new StatementDTO();
            statementDTO.setAccountType(statement.getAccount().getAccountType());
            statementDTO.setAccountNumber(hashString(statement.getAccount().getAccountNumber()));
            statementDTO.setAmount(new BigDecimal(statement.getAmount()));
            try {
                statementDTO.setDatefield(new SimpleDateFormat("dd.MM.yyyy").parse(statement.getDatefield()));
            } catch (ParseException e) {
                logger.error(e.getLocalizedMessage());
                return;
            }
            statementDTOList.add(statementDTO);
        });
        return statementDTOList;
    }
    public static String hashString(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getLocalizedMessage());
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
