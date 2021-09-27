package com.overactive.java.assessment.service;

import com.overactive.java.assessment.entity.Transaction;
import com.overactive.java.assessment.response.TransactionResponseForRewards;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface TransactionService {
    ArrayList<TransactionResponseForRewards> findAll();

    List<TransactionResponseForRewards> findAllApplicableTransactionsByClient(String clientid);

    ArrayList<TransactionResponseForRewards> saveTransaction(Transaction transaction);

    ArrayList<TransactionResponseForRewards> findTransaction(Optional<Long> transactionId)
            throws ResponseStatusException;

    ArrayList<TransactionResponseForRewards> removeTransaction(Long transactionId);

    ArrayList<TransactionResponseForRewards> editTransaction(Transaction transaction);
}
