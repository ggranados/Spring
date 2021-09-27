package com.overactive.java.assessment.service;

import com.overactive.java.assessment.entity.Transaction;
import com.overactive.java.assessment.repository.TransactionRepository;
import com.overactive.java.assessment.response.TransactionResponseForRewards;
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
public class TransactionServiceImpl implements TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private static TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public ArrayList<TransactionResponseForRewards> findAll() {
        logger.debug("Getting all transactions");
        return transactionRepository.findAll()
                .stream()
                .map(t -> new TransactionResponseForRewards(t))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<TransactionResponseForRewards> findAllApplicableTransactionsByClient(String clientid) {
        logger.debug("Getting all applicable transactions by client " + clientid);
        return transactionRepository.findTransactionByClientIdAndApplicable(clientid, Boolean.TRUE)
                .stream()
                .map(t -> new TransactionResponseForRewards(t))
                .collect(Collectors.toCollection(ArrayList::new));
    }


    @Override
    public ArrayList<TransactionResponseForRewards> saveTransaction(Transaction transaction) {
        logger.debug("Saving transaction " + transaction);
        ArrayList<TransactionResponseForRewards> list = new ArrayList<>();
        Transaction t = transactionRepository.save(transaction);
        list.add(new TransactionResponseForRewards(t));
        return list;
    }

    @Override
    public ArrayList<TransactionResponseForRewards> findTransaction(Optional<Long> transactionId)
            throws ResponseStatusException{
        logger.debug("Getting transactions with id " + transactionId);
        Optional<Transaction> t = transactionRepository.findById(transactionId.get());
        if(!t.isPresent()){
            logger.debug("No transactions with id " + transactionId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found: " + transactionId);
        }
        ArrayList<TransactionResponseForRewards> list = new ArrayList<>();
        list.add(new TransactionResponseForRewards(t.get()));
        return list;
    }

    @Override
    public ArrayList<TransactionResponseForRewards> removeTransaction(Long transactionId) {
        logger.debug("Removing transactions with id " + transactionId);
        Optional<Transaction> t = transactionRepository.findById(transactionId);
        if(!t.isPresent()){
            logger.debug("No transactions with id " + transactionId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found: " + transactionId);
        }
        transactionRepository.delete(t.get());

        return new ArrayList<>();
    }

    @Override
    public ArrayList<TransactionResponseForRewards> editTransaction(Transaction transaction) {
        logger.debug("Editing transactions with id " + transaction);
        Optional<Transaction> t = transactionRepository.findById(transaction.getId());
        if(!t.isPresent()){
            logger.debug("No transactions with id " + transaction.getId());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found: " + transaction.getId());
        }
        transactionRepository.save(transaction);

        ArrayList<TransactionResponseForRewards> list = new ArrayList<>();
        list.add(new TransactionResponseForRewards(t.get()));
        return list;
    }
}
