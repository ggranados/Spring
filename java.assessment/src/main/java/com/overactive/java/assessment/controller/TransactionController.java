package com.overactive.java.assessment.controller;

import com.overactive.java.assessment.entity.Transaction;
import com.overactive.java.assessment.response.GenericRestResponse;
import com.overactive.java.assessment.response.TransactionResponse;
import com.overactive.java.assessment.response.TransactionResponseForRewards;
import com.overactive.java.assessment.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(path = "api/v1/transactions")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private static final String API_V = "v1";
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(
            TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final GenericRestResponse<? extends TransactionResponse> handleValidationExceptions
            (Exception ex, WebRequest request) {

        return getGenericErrorRestResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
    }


    @GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all transaction",
            notes = "Gets all transactions persisted",
            response = TransactionResponseForRewards.class)
    public GenericRestResponse<? extends TransactionResponse> getAllTransactions(
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

        logger.info(httpServletRequest.getMethod() + ":" + httpServletRequest.getRequestURI());

        ArrayList<TransactionResponseForRewards> resultList;
        try {

            logger.info("Get transactions");
            resultList = transactionService.findAll();
            logger.debug("resultList:" + resultList.toString());

            if (resultList.isEmpty()) {
                logger.error("Transactions not found");
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Transactions not found"
                );
            }

            GenericRestResponse<? extends TransactionResponse>
                    response = getGenericRestResponse(resultList, HttpStatus.OK.toString(), "");

            logger.debug("response:"+response);
            return response;
        }catch(ResponseStatusException rse) {
            rse.printStackTrace();
            httpServletResponse.setStatus(rse.getStatus().value());
            return getGenericErrorRestResponse(rse.getMessage(), rse.getStatus().value());

        }catch (Exception e){
            e.printStackTrace();
            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return getGenericErrorRestResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }


    @GetMapping(value= "/{tranId}", produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get transaction",
            notes = "Get transaction by ID",
            response = TransactionResponseForRewards.class)
    public GenericRestResponse<? extends TransactionResponse> getTransaction(
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @ApiParam(value = "Transaction identification", required = true)
            @PathVariable("tranId") Long transactionId){

        logger.info(httpServletRequest.getMethod() + ":" + httpServletRequest.getRequestURI());

        ArrayList<TransactionResponseForRewards> resultList;
        try {

            logger.info("Get transaction by id: " + transactionId);
            resultList = transactionService.findTransaction(transactionId);
            logger.debug("resultList:" + resultList.toString());

            if (resultList.isEmpty()) {
                logger.error("Transactions not found");
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Transactions not found"
                );
            }

            GenericRestResponse<? extends TransactionResponse>
                    response = getGenericRestResponse(resultList, HttpStatus.OK.toString(), "");

            logger.debug("response:"+response);
            return response;
        }catch(ResponseStatusException rse) {
            rse.printStackTrace();
            httpServletResponse.setStatus(rse.getStatus().value());
            return getGenericErrorRestResponse(rse.getMessage(), rse.getStatus().value());

        }catch (Exception e){
            e.printStackTrace();
            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return getGenericErrorRestResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Save transaction",
            notes = "Persist a new transaction to DB",
            response = GenericRestResponse.class)
    public GenericRestResponse<? extends TransactionResponse> saveTransaction(
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @ApiParam(value = "Transaction data", required = true)
            @RequestBody @Valid Transaction transaction){
        logger.info(httpServletRequest.getMethod() + ":" + httpServletRequest.getRequestURI());

        transaction.setDate(new Date());
        logger.debug("Transaction:" + transaction);


        try {
            logger.info("Save transaction: " + transaction);
            ArrayList<TransactionResponseForRewards> resultList
                = transactionService.saveTransaction(transaction);
            logger.debug("resultList:" + resultList.toString());

            GenericRestResponse<? extends TransactionResponse>
                    response = getGenericRestResponse(resultList, HttpStatus.CREATED.toString(), "");

            httpServletResponse.setStatus(HttpStatus.CREATED.value());
            logger.debug("response:"+response);
            return response;
        }catch(ResponseStatusException rse) {
            rse.printStackTrace();
            httpServletResponse.setStatus(rse.getStatus().value());
            return getGenericErrorRestResponse(rse.getMessage(), rse.getStatus().value());

        }catch (Exception e){
            e.printStackTrace();
            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return getGenericErrorRestResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @DeleteMapping(value="/{tranId}", produces= MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete transaction by ID",
            notes = "Removes a transaction by ID from DB",
            response = GenericRestResponse.class)
    public GenericRestResponse<? extends TransactionResponse> removeTransaction(
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @ApiParam(value = "Transaction identification", required = true)
            @PathVariable("tranId") Optional<Long> transactionId){

        logger.info(httpServletRequest.getMethod() + ":" + httpServletRequest.getRequestURI());

        ArrayList<TransactionResponseForRewards> resultList;

        try {

            if(transactionId.isEmpty()) {
                logger.error("Transaction Id was expected");
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Transaction Id expected");
            }

            logger.info("Remove transaction by id: " + transactionId);
            resultList = transactionService.removeTransaction(transactionId.get());

            GenericRestResponse<? extends TransactionResponse>
                    response = getGenericRestResponse(resultList, HttpStatus.OK.toString(), "");

            logger.debug("response:"+response);
            return response;
        }catch(ResponseStatusException rse) {
            rse.printStackTrace();
            httpServletResponse.setStatus(rse.getStatus().value());
            return getGenericErrorRestResponse(rse.getMessage(), rse.getStatus().value());

        }catch (Exception e){
            e.printStackTrace();
            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return getGenericErrorRestResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @PutMapping(consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Edit transaction by ID",
            notes = "Edits a persisted transaction",
            response = TransactionResponseForRewards.class)
    public GenericRestResponse<? extends TransactionResponse> editTransaction(
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @ApiParam(value = "Transaction data", required = true)
            @RequestBody @Valid Transaction transaction){

        logger.info(httpServletRequest.getMethod() + ":" + httpServletRequest.getRequestURI());

        ArrayList<TransactionResponseForRewards> resultList;

        try {

            if(transaction.getId() == null) {
                logger.error("Transaction Id was expected");
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Transaction Id expected");
            }

            logger.info("Edit transaction: " + transaction);
            resultList = transactionService.editTransaction(transaction);

            GenericRestResponse<TransactionResponseForRewards>
                    response = getGenericRestResponse(resultList, HttpStatus.OK.toString(), "");

            logger.debug("response:"+response);
            return response;
        }catch(ResponseStatusException rse) {
            rse.printStackTrace();
            httpServletResponse.setStatus(rse.getStatus().value());
            return getGenericErrorRestResponse(rse.getMessage(), rse.getStatus().value());

        }catch (Exception e){
            e.printStackTrace();
            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return getGenericErrorRestResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    private GenericRestResponse<TransactionResponseForRewards> getGenericRestResponse(
            ArrayList<TransactionResponseForRewards> resultList, String responseCode, String errorMessage) {
        GenericRestResponse<TransactionResponseForRewards>
                response = new GenericRestResponse<>(
                resultList, new GenericRestResponse.GenericMetadata(
                API_V, new Date(), responseCode, errorMessage)
        );
        logger.error(errorMessage);
        logger.debug("response:"+response);
        return response;
    }

    private GenericRestResponse<? extends TransactionResponse> getGenericErrorRestResponse
            (String exMessage, Integer errorCode) {

        return getGenericRestResponse(new ArrayList<>(), errorCode.toString(), exMessage);

    }
}
