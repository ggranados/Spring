package edu.ggranados.rewardpoints.api.service;

import edu.ggranados.rewardpoints.api.entity.Transaction;
import edu.ggranados.rewardpoints.api.entity.TransactionPage;
import edu.ggranados.rewardpoints.api.entity.TransactionSearchCriteria;
import edu.ggranados.rewardpoints.api.response.TransactionResponseForRewards;
import org.springframework.data.domain.Page;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;


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
