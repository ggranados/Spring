package com.overactive.java.assessment.controller;

import com.overactive.java.assessment.response.GenericRestResponse;
import com.overactive.java.assessment.response.RewardPointsResponse;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import static com.overactive.java.assessment.response.GenericRestResponse.getGenericErrorRestResponse;

public class GenericController {
    protected static final String API_V = "v1";

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final GenericRestResponse<RewardPointsResponse> handleValidationExceptions
            (Exception ex, WebRequest request) {
        return getGenericErrorRestResponse(ex.getMessage(), API_V, HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final GenericRestResponse<RewardPointsResponse> handleNotFoundExceptions
            (Exception ex, WebRequest request) {
        return getGenericErrorRestResponse(ex.getMessage(), API_V, HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final GenericRestResponse<RewardPointsResponse> handleUnknownExceptions
            (Exception ex, WebRequest request) {
        return getGenericErrorRestResponse(ex.getMessage(), API_V, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
