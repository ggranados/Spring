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
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static com.overactive.java.assessment.response.GenericRestResponse.getGenericRestResponse;

@RestController
@RequestMapping(path = "api/v1/transactions")
public class TransactionController extends GenericController{

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(
            TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all transaction", notes = "Gets all transactions persisted", response = TransactionResponseForRewards.class)
    public GenericRestResponse<?> getAllTransactions(
            HttpServletRequest httpServletRequest) throws Exception {

        logger.info(httpServletRequest.getMethod() + ":" + httpServletRequest.getRequestURI());

        ArrayList<TransactionResponseForRewards> resultList;

        logger.info("Get transactions");
        resultList = transactionService.findAll();
        logger.debug("resultList:" + resultList.toString());

        if (resultList.isEmpty()) {
            logger.error("Transactions not found");
            throw new NotFoundException("Transactions not found");
        }

        var response =
                getGenericRestResponse(resultList, API_V, HttpStatus.OK.toString(), "");

        logger.debug("response:"+response);
        return response;

    }


    @GetMapping(value= "/{tranId}", produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get transaction",notes = "Get transaction by ID",response = TransactionResponseForRewards.class)
    public GenericRestResponse<?> getTransaction(
            HttpServletRequest httpServletRequest,
            @ApiParam(value = "Transaction identification", required = true)
            @PathVariable("tranId")
                    Long transactionId) throws Exception {

        logger.info(httpServletRequest.getMethod() + ":" + httpServletRequest.getRequestURI());

        logger.info("Get transaction by id: " + transactionId);
        var resultList = transactionService.findTransaction(transactionId);
        logger.debug("resultList:" + resultList.toString());

        if (resultList.isEmpty()) {
            logger.error("Transactions not found");
            throw new NotFoundException("Transactions not found");
        }

        var response =
                getGenericRestResponse(resultList, API_V, HttpStatus.OK.toString(), "");

        logger.debug("response:"+response);
        return response;

    }

    @PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Save transaction",notes = "Persist a new transaction to DB",response = GenericRestResponse.class)
    public GenericRestResponse<?> saveTransaction(
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @ApiParam(value = "Transaction data", required = true)
            @RequestBody @Valid
                    Transaction transaction){
        logger.info(httpServletRequest.getMethod() + ":" + httpServletRequest.getRequestURI());

        transaction.setDate(new Date());
        logger.debug("Transaction:" + transaction);

        logger.info("Save transaction: " + transaction);
        var resultList = transactionService.saveTransaction(transaction);
        logger.debug("resultList:" + resultList.toString());

        var response =
                getGenericRestResponse(resultList, API_V, HttpStatus.CREATED.toString(), "");

        httpServletResponse.setStatus(HttpStatus.CREATED.value());
        logger.debug("response:"+response);
        return response;
    }

    @DeleteMapping(value="/{tranId}", produces= MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete transaction by ID", notes = "Removes a transaction by ID from DB", response = GenericRestResponse.class)
    public GenericRestResponse<?> removeTransaction(
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @ApiParam(value = "Transaction identification", required = true)
            @PathVariable("tranId")
                    Optional<Long> transactionId) throws Exception {

        logger.info(httpServletRequest.getMethod() + ":" + httpServletRequest.getRequestURI());

        if(transactionId.isEmpty()) {
            logger.error("Transaction Id was expected");
            throw new NotFoundException("Transaction Id expected");
        }

        logger.info("Remove transaction by id: " + transactionId);
        var resultList = transactionService.removeTransaction(transactionId.get());
        logger.debug("resultList:" + resultList.toString());

        var response =
                getGenericRestResponse(resultList, API_V, HttpStatus.OK.toString(), "");

        logger.debug("response:"+response);
        return response;
    }

    @PutMapping(consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Edit transaction by ID", notes = "Edits a persisted transaction", response = TransactionResponseForRewards.class)
    public GenericRestResponse<?> editTransaction(
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @ApiParam(value = "Transaction data", required = true)
            @RequestBody @Valid
                    Transaction transaction) throws Exception {

        logger.info(httpServletRequest.getMethod() + ":" + httpServletRequest.getRequestURI());

        if(transaction.getId() == null) {
            logger.error("Transaction Id was expected");
            throw new NotFoundException("Transaction Id expected");
        }

        logger.info("Edit transaction: " + transaction);
        var resultList = transactionService.editTransaction(transaction);
        logger.debug("resultList:" + resultList.toString());

        var response =
                getGenericRestResponse(resultList, API_V, HttpStatus.OK.toString(), "");

        logger.debug("response:"+response);
        return response;
    }

}
