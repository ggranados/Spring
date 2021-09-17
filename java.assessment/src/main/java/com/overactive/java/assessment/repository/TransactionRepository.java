package com.overactive.java.assessment.repository;

import com.overactive.java.assessment.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    Transaction save(Transaction transaction);

    List<Transaction> findTransactionByClientIdAndApplicable(String clientId, Boolean applicable);

}
