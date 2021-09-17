package com.overactive.java.assessment.service;

import com.overactive.java.assessment.entity.Transaction;
import com.overactive.java.assessment.repository.TransactionRepository;
import com.overactive.java.assessment.response.TransactionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private static TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public ArrayList<TransactionResponse> findAll() {
        logger.debug("Getting all transactions");
        return transactionRepository.findAll()
                .stream()
                .map(t -> new TransactionResponse(t))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Transaction> findAllApplicableTransactionsByClient(String clientid) {
        logger.debug("Getting all applicable transactions by client " + clientid);
        return transactionRepository.findTransactionByClientIdAndApplicable(clientid, Boolean.TRUE);
    }


    public ArrayList<TransactionResponse> saveTransaction(Transaction transaction) {
        logger.debug("Saving transaction " + transaction);
        ArrayList<TransactionResponse> list = new ArrayList<>();
        Transaction t = transactionRepository.save(transaction);
        list.add(new TransactionResponse(t));
        return list;
    }

    public ArrayList<? extends TransactionResponse> findTransaction(Optional<Long> transactionId)
            throws ResponseStatusException{
        logger.debug("Getting transactions with id " + transactionId);
        Optional<Transaction> t = transactionRepository.findById(transactionId.get());
        if(!t.isPresent()){
            logger.debug("No transactions with id " + transactionId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found: " + transactionId);
        }
        ArrayList<TransactionResponse> list = new ArrayList<>();
        list.add(new TransactionResponse(t.get()));
        return list;
    }

    public ArrayList<? extends TransactionResponse> removeTransaction(Long transactionId) {
        logger.debug("Removing transactions with id " + transactionId);
        Optional<Transaction> t = transactionRepository.findById(transactionId);
        if(!t.isPresent()){
            logger.debug("No transactions with id " + transactionId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found: " + transactionId);
        }
        transactionRepository.delete(t.get());
        return new ArrayList<>();
    }
}
