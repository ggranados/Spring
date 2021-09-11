package com.overactive.java.assessment.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    List<Transaction> findTransactionByClientIdAndApplicable(String clientId, Boolean applicable);

}
