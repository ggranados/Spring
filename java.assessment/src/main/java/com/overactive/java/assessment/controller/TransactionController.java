package com.overactive.java.assessment.controller;

import com.overactive.java.assessment.entity.Transaction;
import com.overactive.java.assessment.response.GenericRestResponse;
import com.overactive.java.assessment.response.TransactionResponse;
import com.overactive.java.assessment.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import static com.overactive.java.assessment.response.ResponseMetadata.*;

@RestController
@RequestMapping(path = "api/v1/transactions")
public class TransactionController {

    private static Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private static final String API_V = "v1";
    private static TransactionService transactionService;

    @Autowired
    public TransactionController(
            TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    private GenericRestResponse<? extends TransactionResponse> getGenericRestResponse
            (HashMap<String, String> metadataMap, String exMessage, Integer errorCode) {
        metadataMap.put(HTTP_RESPONSE, errorCode.toString());
        metadataMap.put(ERROR_MESSAGE, exMessage);
        GenericRestResponse<? extends TransactionResponse>
                response = new GenericRestResponse<>(null, metadataMap);
        logger.error(exMessage);
        logger.debug("response:"+response.toString());
        return response;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final GenericRestResponse<? extends TransactionResponse> handleValidationExceptions
            (Exception ex, WebRequest request) {
        HashMap<String, String> metadataMap = new HashMap<>();
        metadataMap.put(API_VERSION, API_V);
        metadataMap.put(REQUEST_DATE,new Date().toString());
        metadataMap.put(HTTP_RESPONSE, String.valueOf(HttpStatus.BAD_REQUEST.value()));
        metadataMap.put(ERROR_MESSAGE, ex.getMessage());
        GenericRestResponse<? extends TransactionResponse>
                response = new GenericRestResponse<>(null,metadataMap);
        logger.error(ex.getMessage());
        logger.debug(response.toString());
        return response;
    }

    @GetMapping()
    public GenericRestResponse<? extends TransactionResponse> getAllTransactions(
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

        logger.info(httpServletRequest.getMethod() + ":" + httpServletRequest.getRequestURI());

        HashMap<String, String> metadataMap = new HashMap<>();
        metadataMap.put(API_VERSION, API_V);
        metadataMap.put(REQUEST_DATE,new Date().toString());


        ArrayList<? extends TransactionResponse> resultList;
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

            metadataMap.put(HTTP_RESPONSE, String.valueOf(HttpStatus.OK.value()));

            GenericRestResponse<? extends TransactionResponse>
                    response = new GenericRestResponse<>(resultList, metadataMap);

            logger.debug("response:"+response.toString());
            return response;
        }catch(ResponseStatusException rse) {
            rse.printStackTrace();
            httpServletResponse.setStatus(rse.getStatus().value());
            return getGenericRestResponse(metadataMap, rse.getMessage(), rse.getStatus().value());

        }catch (Exception e){
            e.printStackTrace();
            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return getGenericRestResponse(metadataMap, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @GetMapping("/{tranId}")
    public GenericRestResponse<? extends TransactionResponse> getAllTransactions(
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @PathVariable("tranId") Optional<Long> transactionId){

        logger.info(httpServletRequest.getMethod() + ":" + httpServletRequest.getRequestURI());

        HashMap<String, String> metadataMap = new HashMap<>();
        metadataMap.put(API_VERSION, API_V);
        metadataMap.put(REQUEST_DATE,new Date().toString());


        ArrayList<? extends TransactionResponse> resultList;
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

            metadataMap.put(HTTP_RESPONSE, String.valueOf(HttpStatus.OK.value()));

            GenericRestResponse<? extends TransactionResponse>
                    response = new GenericRestResponse<>(resultList, metadataMap);

            logger.debug("response:"+response.toString());
            return response;
        }catch(ResponseStatusException rse) {
            rse.printStackTrace();
            httpServletResponse.setStatus(rse.getStatus().value());
            return getGenericRestResponse(metadataMap, rse.getMessage(), rse.getStatus().value());

        }catch (Exception e){
            e.printStackTrace();
            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return getGenericRestResponse(metadataMap, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @PostMapping
    public GenericRestResponse<? extends TransactionResponse> saveTransaction(
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestBody @Valid Transaction transaction){
        logger.info(httpServletRequest.getMethod() + ":" + httpServletRequest.getRequestURI());

        transaction.setDate(new Date());
        logger.debug("Transaction:" + transaction);

        HashMap<String, String> metadataMap = new HashMap<>();
        metadataMap.put(API_VERSION, API_V);
        metadataMap.put(REQUEST_DATE,new Date().toString());

        try {
            logger.info("Save transaction: " + transaction);
            ArrayList<? extends TransactionResponse> resultList
                = transactionService.saveTransaction(transaction);
            logger.debug("resultList:" + resultList.toString());

            metadataMap.put(HTTP_RESPONSE, String.valueOf(HttpStatus.OK.value()));

            GenericRestResponse<? extends TransactionResponse>
                    response = new GenericRestResponse<>(resultList, metadataMap);

            logger.debug("response:"+response.toString());
            return response;
        }catch(ResponseStatusException rse) {
            rse.printStackTrace();
            httpServletResponse.setStatus(rse.getStatus().value());
            return getGenericRestResponse(metadataMap, rse.getMessage(), rse.getStatus().value());

        }catch (Exception e){
            e.printStackTrace();
            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return getGenericRestResponse(metadataMap, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @DeleteMapping("/{tranId}")
    public GenericRestResponse<? extends TransactionResponse> removeTransaction(
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @PathVariable("tranId") Optional<Long> transactionId){

        logger.info(httpServletRequest.getMethod() + ":" + httpServletRequest.getRequestURI());

        HashMap<String, String> metadataMap = new HashMap<>();
        metadataMap.put(API_VERSION, API_V);
        metadataMap.put(REQUEST_DATE,new Date().toString());

        ArrayList<? extends TransactionResponse> resultList;

        try {

            if(transactionId.isEmpty()) {
                logger.error("Transaction Id was expected");
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Transaction Id expected");
            }

            logger.info("Remove transaction by id: " + transactionId);
            resultList = transactionService.removeTransaction(transactionId.get());

            metadataMap.put(HTTP_RESPONSE, String.valueOf(HttpStatus.OK.value()));

            GenericRestResponse<? extends TransactionResponse>
                    response = new GenericRestResponse<>(resultList, metadataMap);

            logger.debug("response:"+response.toString());
            return response;
        }catch(ResponseStatusException rse) {
            rse.printStackTrace();
            httpServletResponse.setStatus(rse.getStatus().value());
            return getGenericRestResponse(metadataMap, rse.getMessage(), rse.getStatus().value());

        }catch (Exception e){
            e.printStackTrace();
            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return getGenericRestResponse(metadataMap, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @PutMapping()
    public GenericRestResponse<? extends TransactionResponse> editTransaction(
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestBody @Valid Transaction transaction){

        logger.info(httpServletRequest.getMethod() + ":" + httpServletRequest.getRequestURI());

        HashMap<String, String> metadataMap = new HashMap<>();
        metadataMap.put(API_VERSION, API_V);
        metadataMap.put(REQUEST_DATE,new Date().toString());

        ArrayList<? extends TransactionResponse> resultList;

        try {

            if(transaction.getId() == null) {
                logger.error("Transaction Id was expected");
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Transaction Id expected");
            }

            logger.info("Edit transaction: " + transaction);
            resultList = transactionService.editTransaction(transaction);

            metadataMap.put(HTTP_RESPONSE, String.valueOf(HttpStatus.OK.value()));

            GenericRestResponse<? extends TransactionResponse>
                    response = new GenericRestResponse<>(resultList, metadataMap);

            logger.debug("response:"+response.toString());
            return response;
        }catch(ResponseStatusException rse) {
            rse.printStackTrace();
            httpServletResponse.setStatus(rse.getStatus().value());
            return getGenericRestResponse(metadataMap, rse.getMessage(), rse.getStatus().value());

        }catch (Exception e){
            e.printStackTrace();
            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return getGenericRestResponse(metadataMap, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
