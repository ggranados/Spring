package com.overactive.java.assessment.controller;

import com.overactive.java.assessment.response.*;
import com.overactive.java.assessment.service.RewardPointsServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import static com.overactive.java.assessment.response.ResponseMetadata.*;

@RestController
@RequestMapping(path = "api/v1/rewards")
@Validated
public class RewardPointsController {

    private static Logger logger = LoggerFactory.getLogger(RewardPointsController.class);
    private static final String API_V = "v1";

    private final RewardPointsServiceImpl rewardPointsService;

    @Autowired
    public RewardPointsController(RewardPointsServiceImpl rewardPointsService){
        this.rewardPointsService = rewardPointsService;
    }

    @GetMapping(path = "clients", produces= MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(value = "Get all reward points",
            notes = "Gets all applicable Reward Points by all clients",
            response = TotalRewardPointsResponse.class)
    public GenericRestResponse<? extends RewardPointsResponse> getRewardPointsByClients(
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

        logger.info(httpServletRequest.getMethod() + ":" + httpServletRequest.getRequestURI());

        HashMap<String, String> metadataMap = new HashMap<>();
        metadataMap.put(API_VERSION, API_V);
        metadataMap.put(REQUEST_DATE,new Date().toString());

        ArrayList<? extends RewardPointsResponse> resultList = null;

        try {

            logger.info("Requested reward points without parameters, assuming all as default");
            resultList = rewardPointsService.getAllRewardPoints();
            logger.debug(resultList.toString());

            if (resultList == null || resultList.isEmpty()) {
                logger.error("Rewards points for client not found");
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Rewards points not found"
                );
            }

            metadataMap.put(HTTP_RESPONSE,String.valueOf(HttpStatus.OK.value()));

            GenericRestResponse<? extends RewardPointsResponse>
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

    @GetMapping(path = "clients/{clientId}", produces=MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(value = "Get reward points by client",
            notes = "Gets reward points by client ID and period ID",
            response = TotalRewardPointsResponse.class)
    public GenericRestResponse<? extends RewardPointsResponse> getRewardPointsByClient(
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @ApiParam(value = "Client identification", required = true)
            @PathVariable("clientId") Optional<String> clientId){

        logger.info(httpServletRequest.getMethod() + ":" + httpServletRequest.getRequestURI());
        logger.debug("Params:[clientId:" + clientId + "]");

        HashMap<String, String> metadataMap = new HashMap<>();
        metadataMap.put(API_VERSION, API_V);
        metadataMap.put(REQUEST_DATE,new Date().toString());

        ArrayList<? extends RewardPointsResponse> resultList = null;

        try {

            if (clientId.isEmpty()) {
                logger.error("Client was expected");
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Client was expected"
                );
            }

            logger.info("Requested default total reward points by client");
            resultList = rewardPointsService.getRewardPointsByClientTotal(clientId.get());
            logger.debug("resultList:" + resultList.toString());


            if (resultList == null || resultList.isEmpty()) {
                logger.error("Rewards points for client: " + clientId + " not found");
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Rewards points for client: " + clientId + " not found"
                );
            }

            metadataMap.put(HTTP_RESPONSE,String.valueOf(HttpStatus.OK.value()));

            GenericRestResponse<? extends RewardPointsResponse>
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

    @GetMapping(path = "clients/{clientId}/{period}", produces=MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(value = "Get reward points by client ID and period ID",
            notes = "Gets all applicable Rewards Points by client and period, which can be monthly or total (as default)",
            response = MonthlyRewardPointsResponse.class)
    public GenericRestResponse<? extends RewardPointsResponse> getRewardPointsByClientByPeriod(
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @ApiParam(value = "Client identification", required = true)
            @PathVariable("clientId") Optional<String> clientId,
            @ApiParam(value = "Period identification", required = true, allowableValues = "MONTHLY,TOTAL")
            @PathVariable("period") Optional<String> period){

        logger.info(httpServletRequest.getMethod() + ":" + httpServletRequest.getRequestURI());
        logger.debug("Params:[clienteId:"+clientId+",period:"+period+"]");

        HashMap<String, String> metadataMap = new HashMap<>();
        metadataMap.put(API_VERSION, API_V);
        metadataMap.put(REQUEST_DATE,new Date().toString());

        ArrayList<? extends RewardPointsResponse> resultList = null;

        try {
            if (!clientId.isPresent() && !period.isPresent()) {
                logger.info("Requested reward points without parameters, assuming all as default");
                resultList = rewardPointsService.getAllRewardPoints();
            } else {

                if (clientId.isEmpty()) {
                    logger.error("Client was expected");
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Client was expected"
                    );
                }

                String periodVal = period.orElse("default").toUpperCase();
                metadataMap.put(POINTS_PERIOD,periodVal);

                switch (periodVal) {
                    case "MONTHLY":
                        logger.info("Requested monthly reward points by client");
                        resultList = rewardPointsService.getRewardPointsByClientMonthly(clientId.get());
                        logger.debug("resultList:" + resultList.toString());
                        break;
                    case "TOTAL":
                    default:
                        logger.info("Requested default total reward points by client");
                        resultList = rewardPointsService.getRewardPointsByClientTotal(clientId.get());
                        logger.debug("resultList:" + resultList.toString());
                        metadataMap.put(POINTS_PERIOD,"Expected [MONTHLY|TOTAL] assumed default TOTAL");
                        break;
                }
            }

            if (resultList == null || resultList.isEmpty()) {
                logger.error("Rewards points for client: " + clientId + " not found");
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Rewards points for client: " + clientId + " not found"
                );
            }

            metadataMap.put(HTTP_RESPONSE,String.valueOf(HttpStatus.OK.value()));

            GenericRestResponse<? extends RewardPointsResponse>
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

    private GenericRestResponse<? extends RewardPointsResponse> getGenericRestResponse
            (HashMap<String, String> metadataMap, String exMessage, Integer errorCode) {
        metadataMap.put(HTTP_RESPONSE, errorCode.toString());
        metadataMap.put(ERROR_MESSAGE, exMessage);
        GenericRestResponse<? extends RewardPointsResponse>
                response = new GenericRestResponse<>(null, metadataMap);
        logger.error(exMessage);
        logger.debug("response:"+response.toString());
        return response;
    }




}
