package com.overactive.java.assessment.service;

import com.overactive.java.assessment.entity.Transaction;
import com.overactive.java.assessment.response.TransactionResponse;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface TransactionService {
    ArrayList<TransactionResponse> findAll();

    List<TransactionResponse> findAllApplicableTransactionsByClient(String clientid);

    ArrayList<TransactionResponse> saveTransaction(Transaction transaction);

    ArrayList<? extends TransactionResponse> findTransaction(Optional<Long> transactionId)
            throws ResponseStatusException;

    ArrayList<? extends TransactionResponse> removeTransaction(Long transactionId);

    ArrayList<? extends TransactionResponse> editTransaction(Transaction transaction);
}
