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
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
        metadataMap.put(RESPONSE_DATE, new Date().toString());
        metadataMap.put(ERROR_MESSAGE, exMessage);
        GenericRestResponse<? extends TransactionResponse>
                response = new GenericRestResponse<>(null, metadataMap);
        logger.error(exMessage);
        logger.debug("Response:"+response.toString());
        return response;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final GenericRestResponse<? extends TransactionResponse> handleAllExceptions
            (Exception ex, WebRequest request) {
        HashMap<String, String> metadataMap = new HashMap<>();
        metadataMap.put(API_VERSION, API_V);
        metadataMap.put(REQUEST_DATE,new Date().toString());
        metadataMap.put(HTTP_RESPONSE, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        metadataMap.put(RESPONSE_DATE, new Date().toString());
        metadataMap.put(ERROR_MESSAGE, ex.getMessage());
        GenericRestResponse<? extends TransactionResponse>
                response = new GenericRestResponse<>(null,metadataMap);
        return response;
    }

    @GetMapping()
    public GenericRestResponse<? extends TransactionResponse> getAllTransactions(
            HttpServletRequest request){

        logger.info(request.getRequestURI());

        HashMap<String, String> metadataMap = new HashMap<>();
        metadataMap.put(API_VERSION, API_V);
        metadataMap.put(REQUEST_DATE,new Date().toString());

        ArrayList<? extends TransactionResponse>
                resultList = transactionService.findAll();
        try {
            if (resultList.isEmpty()) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Transactions not found"
                );
            }

            metadataMap.put(HTTP_RESPONSE, String.valueOf(HttpStatus.OK.value()));
            metadataMap.put(RESPONSE_DATE, new Date().toString());

            GenericRestResponse<? extends TransactionResponse>
                    response = new GenericRestResponse<>(resultList, metadataMap);

            return response;
        }catch(ResponseStatusException rse) {
            rse.printStackTrace();
            return getGenericRestResponse(metadataMap, rse.getMessage(), rse.getStatus().value());

        }catch (Exception e){
            e.printStackTrace();
            return getGenericRestResponse(metadataMap, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @PostMapping
    public GenericRestResponse<? extends TransactionResponse> saveTransaction(
            HttpServletRequest request,
            @RequestBody @Valid Transaction transaction){
        logger.info(request.getRequestURI());

        transaction.setDate(new Date());
        logger.debug("Transaction:" + transaction);

        HashMap<String, String> metadataMap = new HashMap<>();
        metadataMap.put(API_VERSION, API_V);
        metadataMap.put(REQUEST_DATE,new Date().toString());

        try {

            ArrayList<? extends TransactionResponse> resultList
                = transactionService.saveTansaction(transaction);


            metadataMap.put(HTTP_RESPONSE, String.valueOf(HttpStatus.OK.value()));
            metadataMap.put(RESPONSE_DATE, new Date().toString());

            GenericRestResponse<? extends TransactionResponse>
                    response = new GenericRestResponse<>(resultList, metadataMap);

            return response;
        }catch(ResponseStatusException rse) {
            rse.printStackTrace();
            return getGenericRestResponse(metadataMap, rse.getMessage(), rse.getStatus().value());

        }catch (Exception e){
            e.printStackTrace();
            return getGenericRestResponse(metadataMap, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

}
