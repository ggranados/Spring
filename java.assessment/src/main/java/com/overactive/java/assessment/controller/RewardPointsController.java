package com.overactive.java.assessment.controller;

import com.overactive.java.assessment.response.*;
import com.overactive.java.assessment.service.RewardPointsServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Optional;

import static com.overactive.java.assessment.response.GenericRestResponse.*;


@RestController
@RequestMapping(path = "api/v1/rewards")
@Validated
public class RewardPointsController {

    private static final Logger logger = LoggerFactory.getLogger(RewardPointsController.class);
    private static final String API_V = "v1";

    private final RewardPointsServiceImpl rewardPointsService;

    @Autowired
    public RewardPointsController(RewardPointsServiceImpl rewardPointsService) {
        this.rewardPointsService = rewardPointsService;
    }

    @GetMapping(path = "clients", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all reward points", notes = "Gets all applicable Reward Points by all clients", response = TotalRewardPointsResponse.class)
    public GenericRestResponse<?> getRewardPointsByClients(
            HttpServletRequest httpServletRequest) throws Exception {

        logger.info(httpServletRequest.getMethod() + ":" + httpServletRequest.getRequestURI());

        ArrayList<? extends RewardPointsResponse> resultList;

        logger.info("Requested reward points without parameters, assuming all as default");
        resultList = rewardPointsService.getAllRewardPoints();
        logger.debug(resultList.toString());
        if (resultList.isEmpty()) {
            logger.error("Rewards points for client not found");
            throw new NotFoundException("Rewards points not found");
        }

        var response =
                getGenericRestResponse(resultList, API_V, HttpStatus.OK.toString(), "");

        logger.debug("response:" + response);
        return response;
    }

    @GetMapping(path = "clients/{clientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get reward points by client", notes = "Gets reward points by client ID and period ID", response = TotalRewardPointsResponse.class)
    public GenericRestResponse<?> getRewardPointsByClient(
            HttpServletRequest httpServletRequest,
            @ApiParam(value = "Client identification", required = true)
            @PathVariable("clientId")
                    Optional<String> clientId) throws Exception {

        logger.info(httpServletRequest.getMethod() + ":" + httpServletRequest.getRequestURI());
        logger.debug("Params:[clientId:" + clientId + "]");

        if (clientId.isEmpty()) {
            logger.error("Client was expected");
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Client was expected"
            );
        }

        logger.info("Requested default total reward points by client");
        var resultList = rewardPointsService.getRewardPointsByClientTotal(clientId.get());
        logger.debug("resultList:" + resultList.toString());

        if (resultList.isEmpty()) {
            logger.error("Rewards points for client: " + clientId.get() + " not found");
            throw new NotFoundException("Rewards points for client: " + clientId.get() + " not found");
        }

        var response =
                getGenericRestResponse(resultList, API_V, HttpStatus.OK.toString(), "");

        logger.debug("response:" + response);
        return response;
    }

    @GetMapping(path = "clients/{clientId}/{period}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get reward points by client ID and period ID", notes = "Gets all applicable Rewards Points by client and period, which can be monthly or total (as default)", response = MonthlyRewardPointsResponse.class)
    public GenericRestResponse<?> getRewardPointsByClientByPeriod(
            HttpServletRequest httpServletRequest,
            @ApiParam(value = "Client identification", required = true)
            @PathVariable("clientId")
                    Optional<String> clientId,
            @ApiParam(value = "Period identification", required = true, allowableValues = "MONTHLY,TOTAL")
            @PathVariable("period")
                    Optional<String> period) throws Exception {

        logger.info(httpServletRequest.getMethod() + ":" + httpServletRequest.getRequestURI());
        logger.debug("Params:[clientId:" + clientId + ",period:" + period + "]");

        ArrayList<?> resultList = null;
        String errorMessage = "";

        boolean periodIsNotIndicated = period.isEmpty();
        if (periodIsNotIndicated) {
            logger.info("Requested reward points without parameters, assuming all rewards as default");
            resultList = rewardPointsService.getAllRewardPoints();
        } else {

            boolean clientIdIsNotIndicated = clientId.isEmpty();
            if (clientIdIsNotIndicated) {
                logger.error("Client was expected");
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Client was expected"
                );
            }

            String periodVal = period.orElse("default").toUpperCase();

            switch (periodVal) {
                case "MONTHLY":
                    logger.info("Requested monthly reward points by client");
                    resultList = rewardPointsService.getRewardPointsByClientMonthly(clientId.get());
                    logger.debug("resultList:" + resultList.toString());
                    break;

                default:
                    errorMessage = "period param: Expected [MONTHLY|TOTAL] assumed default TOTAL";
                case "TOTAL":
                    logger.info("Requested default total reward points by client");
                    resultList = rewardPointsService.getRewardPointsByClientTotal(clientId.get());
                    logger.debug("resultList:" + resultList.toString());
                    break;
            }
        }

        boolean noResultsFound = resultList == null || resultList.isEmpty();
        if (noResultsFound) {
            logger.error("Rewards points for client: " + clientId + " not found");
            throw new NotFoundException("Rewards points for client: " + clientId + " not found");
        }

        var response =
                getGenericRestResponse(resultList, API_V, HttpStatus.OK.toString(), errorMessage);

        logger.debug("response:" + response.toString());
        return response;
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final GenericRestResponse<?> handleValidationExceptions
            (Exception ex, WebRequest request) {
        return getGenericErrorRestResponse(ex.getMessage(), API_V, HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final GenericRestResponse<?> handleNotFoundExceptions
            (Exception ex, WebRequest request) {
        return getGenericErrorRestResponse(ex.getMessage(), API_V, HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final GenericRestResponse<?> handleUnknownExceptions
            (Exception ex, WebRequest request) {
        return getGenericErrorRestResponse(ex.getMessage(), API_V, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
