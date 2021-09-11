package com.overactive.java.assessment.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private static TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionResponse> findAll() {
        return transactionRepository.findAll()
                .stream()
                .map(t -> new TransactionResponse(t))
                .collect(Collectors.toList());
    }

    public List<Transaction> findAllApplicableTransactionsByClient(String clientID) {
        return transactionRepository.findTransactionByClientIdAndApplicable(clientID, Boolean.TRUE);
    }
}
