package com.overactive.java.assessment.service;

import com.overactive.java.assessment.entity.Transaction;
import com.overactive.java.assessment.repository.TransactionRepository;
import com.overactive.java.assessment.response.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private static TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public ArrayList<TransactionResponse> findAll() {
        return transactionRepository.findAll()
                .stream()
                .map(t -> new TransactionResponse(t))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Transaction> findAllApplicableTransactionsByClient(String clientID) {
        return transactionRepository.findTransactionByClientIdAndApplicable(clientID, Boolean.TRUE);
    }


    public ArrayList<TransactionResponse> saveTansaction(Transaction transaction) {
        ArrayList<TransactionResponse> list = new ArrayList<>();
        Transaction t = transactionRepository.save(transaction);
        list.add(new TransactionResponse(t));
        return list;
    }
}
