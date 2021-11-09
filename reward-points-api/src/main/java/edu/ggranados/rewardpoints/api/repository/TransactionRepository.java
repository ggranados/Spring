package edu.ggranados.rewardpoints.api.repository;

import edu.ggranados.rewardpoints.api.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    Transaction save(Transaction transaction);

    List<Transaction> findTransactionByClientIdAndApplicable(String clientId, Boolean applicable);

}
