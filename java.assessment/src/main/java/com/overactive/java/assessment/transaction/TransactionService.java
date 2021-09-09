package com.overactive.java.assessment.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private static TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public List<Transaction> findAllByClient(String clientID) {
        return transactionRepository.findTransactionByClientId(clientID);
    }
}
