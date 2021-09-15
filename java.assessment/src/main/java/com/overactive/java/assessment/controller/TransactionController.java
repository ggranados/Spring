package com.overactive.java.assessment.controller;

import com.overactive.java.assessment.response.TransactionResponse;
import com.overactive.java.assessment.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/rewards")
public class TransactionController {

    private static TransactionService transactionService;

    @Autowired
    public TransactionController(
            TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping(path = "transactions/all")
    public List<TransactionResponse> getAllTransactions(){
        List<TransactionResponse>
            response = transactionService.findAll();

        if(response.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Transactions not found"
            );
        }
        return response;
    }
}
