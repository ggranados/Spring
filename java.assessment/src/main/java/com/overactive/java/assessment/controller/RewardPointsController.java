package com.overactive.java.assessment.controller;

import com.overactive.java.assessment.response.GenericRestResponse;
import com.overactive.java.assessment.response.RewardPointsResponse;
import com.overactive.java.assessment.service.RewardPointsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

import static com.overactive.java.assessment.response.ResponseMetadata.*;

import java.util.*;

@RestController
@RequestMapping(path = "api/v1/rewards")
@Validated
public class RewardPointsController {

    private static Logger logger = LoggerFactory.getLogger(RewardPointsController.class);
    private static final String API_V = "1";

    private final RewardPointsService rewardPointsService;

    @Autowired
    public RewardPointsController(RewardPointsService rewardPointsService){
        this.rewardPointsService = rewardPointsService;
    }

    @GetMapping(path = "clients")
    public GenericRestResponse<? extends RewardPointsResponse> getRewardPointsByClientMonthly(
            HttpServletRequest request,
            @RequestParam("clientId") Optional<String> clientId,
            @RequestParam("period") Optional<String> period){

        logger.info(request.getRequestURI());
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
                        break;
                    case "TOTAL":
                    default:
                        logger.info("Requested default total reward points by client");
                        resultList = rewardPointsService.getRewardPointsByClientTotal(clientId.get());
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
            metadataMap.put(RESPONSE_DATE, new Date().toString());

            GenericRestResponse<? extends RewardPointsResponse>
                    response = new GenericRestResponse<>(resultList, metadataMap);

            logger.debug("Response:"+response.toString());
            return response;

        }catch(ResponseStatusException rse) {
            metadataMap.put(HTTP_RESPONSE, String.valueOf(rse.getStatus().value()));
            metadataMap.put(RESPONSE_DATE, new Date().toString());
            metadataMap.put(ERROR_MESSAGE, rse.getMessage());
            GenericRestResponse<? extends RewardPointsResponse> response = new GenericRestResponse<>(null, metadataMap);
            logger.error(rse.getMessage());
            rse.printStackTrace();
            logger.debug("Response:"+response.toString());
            return response;

        }catch (Exception e){
            metadataMap.put(HTTP_RESPONSE, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
            metadataMap.put(RESPONSE_DATE, new Date().toString());
            metadataMap.put(ERROR_MESSAGE, e.getMessage());
            GenericRestResponse<? extends RewardPointsResponse> response = new GenericRestResponse<>(null, metadataMap);
            logger.error(e.getMessage());
            e.printStackTrace();
            logger.debug("Response:"+response.toString());
            return response;
        }
    }




}
