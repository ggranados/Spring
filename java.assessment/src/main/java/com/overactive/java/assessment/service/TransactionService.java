package com.overactive.java.assessment.service;

import com.overactive.java.assessment.entity.Transaction;
import com.overactive.java.assessment.entity.TransactionPage;
import com.overactive.java.assessment.entity.TransactionSearchCriteria;
import com.overactive.java.assessment.response.TransactionResponseForRewards;
import org.springframework.data.domain.Page;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public interface TransactionService {
    ArrayList<TransactionResponseForRewards> findAll();

    List<TransactionResponseForRewards> findAllApplicableTransactionsByClient(String clientId);

    ArrayList<TransactionResponseForRewards> saveTransaction(Transaction transaction);

    ArrayList<TransactionResponseForRewards> findTransaction(Long transactionId)
            throws ResponseStatusException;

    ArrayList<TransactionResponseForRewards> removeTransaction(Long transactionId)
            throws ResponseStatusException;

    ArrayList<TransactionResponseForRewards> editTransaction(Transaction transaction, Long transactionId);

    Page<Transaction> getTransactions(TransactionPage page,
                                             TransactionSearchCriteria searchCriteria);
}
