package com.overactive.java.assessment.service;

import com.overactive.java.assessment.entity.Transaction;

import java.math.BigDecimal;
import java.util.Date;

public class TestData {

    protected static final Transaction trxApplicableForBoth;
    static {
        trxApplicableForBoth = new Transaction(1L,"CLI001",new BigDecimal(120),new Date(),true);
    }
}
