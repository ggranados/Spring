package com.overactive.java.assessment.controller;

import com.overactive.java.assessment.entity.Transaction;
import com.overactive.java.assessment.response.GenericRestResponse;
import com.overactive.java.assessment.response.TransactionResponseForRewards;
import com.overactive.java.assessment.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;

import static com.overactive.java.assessment.response.GenericRestResponse.getGenericRestResponse;

@RestController
@RequestMapping(path = "api/v1/transactions")
public class TransactionController extends GenericController{

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    public static final String TRANSACTIONS_NOT_FOUND = "Transactions not found";
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(
            TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all transaction", notes = "Gets all transactions persisted", response = TransactionResponseForRewards.class)
    public GenericRestResponse<TransactionResponseForRewards> getAllTransactions(
            HttpServletRequest httpServletRequest) throws NotFoundException {

        logRequest(httpServletRequest);

        logger.info("Get transactions");
        var resultList = transactionService.findAll();

        if (resultList.isEmpty()) {
            logger.error(TRANSACTIONS_NOT_FOUND);
            throw new NotFoundException(TRANSACTIONS_NOT_FOUND);
        }

        var response =
                getGenericRestResponse(resultList, API_V, HttpStatus.OK.toString(), "");

        if(logger.isDebugEnabled()){
            logResults(resultList.toString());
            logResponse(response);
        }
        return response;

    }


    @GetMapping(value= "/{tranId}", produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get transaction",notes = "Get transaction by ID",response = TransactionResponseForRewards.class)
    public GenericRestResponse<TransactionResponseForRewards> getTransaction(
            HttpServletRequest httpServletRequest,
            @ApiParam(value = "Transaction identification", required = true)
            @PathVariable("tranId")
                    Long transactionId) throws NotFoundException {

        logRequest(httpServletRequest);

        logger.info("Get transaction by id: {}", transactionId);
        var resultList =
                transactionService.findTransaction(transactionId);

        if (resultList.isEmpty()) {
            logger.error(TRANSACTIONS_NOT_FOUND);
            throw new NotFoundException(TRANSACTIONS_NOT_FOUND);
        }

        var response =
                getGenericRestResponse(resultList, API_V, HttpStatus.OK.toString(), "");

        if(logger.isDebugEnabled()){
            logResults(resultList.toString());
            logResponse(response);
        }
        return response;

    }

    @PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Save transaction",notes = "Persist a new transaction to DB",response = GenericRestResponse.class)
    public GenericRestResponse<TransactionResponseForRewards> saveTransaction(
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @ApiParam(value = "Transaction data", required = true)
            @RequestBody @Valid
                    Transaction transaction){

        logRequest(httpServletRequest);

        transaction.setDate(new Date());
        logger.debug("Transaction: {}", transaction);

        logger.info("Save transaction: {}",transaction);
        var resultList =
                transactionService.saveTransaction(transaction);

        var response =
                getGenericRestResponse(resultList, API_V, HttpStatus.CREATED.toString(), "");

        httpServletResponse.setStatus(HttpStatus.CREATED.value());
        if(logger.isDebugEnabled()){
            logResults(resultList.toString());
            logResponse(response);
        }
        return response;
    }

    @DeleteMapping(value="/{tranId}", produces= MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete transaction by ID", notes = "Removes a transaction by ID from DB", response = GenericRestResponse.class)
    public GenericRestResponse<TransactionResponseForRewards> removeTransaction(
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @ApiParam(value = "Transaction identification", required = true)
            @PathVariable("tranId")
                    Optional<Long> transactionId) throws NotFoundException {

        logRequest(httpServletRequest);

        if(transactionId.isEmpty()) {
            logger.error("Transaction Id was expected");
            throw new NotFoundException("Transaction Id expected");
        }

        logger.info("Remove transaction by id: {}", transactionId);
        var resultList =
                transactionService.removeTransaction(transactionId.get());

        var response =
                getGenericRestResponse(resultList, API_V, HttpStatus.OK.toString(), "");

        if(logger.isDebugEnabled()){
            logResults(resultList.toString());
            logResponse(response);
        }
        return response;
    }

    @PutMapping(consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Edit transaction by ID", notes = "Edits a persisted transaction", response = TransactionResponseForRewards.class)
    public GenericRestResponse<TransactionResponseForRewards> editTransaction(
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @ApiParam(value = "Transaction data", required = true)
            @RequestBody @Valid
                    Transaction transaction) throws NotFoundException {

        logRequest(httpServletRequest);

        if(transaction.getId() == null) {
            logger.error("Transaction Id was expected");
            throw new NotFoundException("Transaction Id expected");
        }

        logger.info("Edit transaction: {}", transaction);
        var resultList =
                transactionService.editTransaction(transaction);

        var response =
                getGenericRestResponse(resultList, API_V, HttpStatus.OK.toString(), "");

        if(logger.isDebugEnabled()){
            logResults(resultList.toString());
            logResponse(response);
        }
        return response;
    }

}
