package com.overactive.java.assessment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.overactive.java.assessment.dto.TransactionDTO;
import com.overactive.java.assessment.entity.Transaction;

import java.math.BigDecimal;
import java.util.Date;

public class TestData {

    public static final Transaction trxApplicableForBoth;
    public static final TransactionDTO trxDtoApplicableForBoth;
    static {
        trxApplicableForBoth = new Transaction(1L,"CLI001",BigDecimal.valueOf(120.00),new Date(),true);
        trxDtoApplicableForBoth = new TransactionDTO(1L,"CLI001",BigDecimal.valueOf(120.00),new Date(),true);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
