package com.overactive.java.assessment.transaction;

import com.overactive.java.assessment.rewardpoints.RewardPointsCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/transactions")
public class TransactionController {

    private static TransactionService transactionService;

    @Autowired
    public TransactionController(
            TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping(path = "all")
    public List<Transaction> getAllRewardPoints(){
        return transactionService.findAll();
    }

}
