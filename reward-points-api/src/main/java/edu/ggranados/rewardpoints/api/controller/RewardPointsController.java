package edu.ggranados.rewardpoints.api.controller;

import edu.ggranados.rewardpoints.api.response.GenericRestResponse;
import edu.ggranados.rewardpoints.api.response.MonthlyRewardPointsResponse;
import edu.ggranados.rewardpoints.api.response.TotalRewardPointsResponse;
import edu.ggranados.rewardpoints.api.service.RewardPointsServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Optional;

import static edu.ggranados.rewardpoints.api.response.GenericRestResponse.*;


@RestController
@RequestMapping(path = "api/v1/rewards")
@Validated
public class RewardPointsController extends GenericController{

    private static final Logger logger = LoggerFactory.getLogger(RewardPointsController.class);
    public static final String CLIENT_WAS_EXPECTED = "Client was expected";
    public static final String POINTS_FOR_CLIENT_S_NOT_FOUND = "Rewards points for client: {} not found";

    private final RewardPointsServiceImpl rewardPointsService;

    @Autowired
    public RewardPointsController(RewardPointsServiceImpl rewardPointsService) {
        this.rewardPointsService = rewardPointsService;
    }

    @GetMapping(path = "clients", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all reward points", notes = "Gets all applicable Reward Points by all clients", response = TotalRewardPointsResponse.class)
    public GenericRestResponse<TotalRewardPointsResponse> getRewardPointsByClients(
            HttpServletRequest httpServletRequest) throws NotFoundException {

        logRequest(httpServletRequest);

        ArrayList<TotalRewardPointsResponse> resultList;

        logger.info("Requested reward points without parameters, assuming all as default");
        resultList = rewardPointsService.getAllRewardPoints();


        if (resultList.isEmpty()) {
            logger.error("Rewards points for client not found");
            throw new NotFoundException("Rewards points not found");
        }

        var response =
                getGenericRestResponse(resultList, API_V, HttpStatus.OK.toString(), "");

        if(logger.isDebugEnabled()){
            logResults(resultList.toString());
            logResponse(response);
        }
        return response;
    }

    @GetMapping(path = "clients/{clientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get reward points by client", notes = "Gets reward points by client ID and period ID", response = TotalRewardPointsResponse.class)
    public GenericRestResponse<TotalRewardPointsResponse> getRewardPointsByClient(
            HttpServletRequest httpServletRequest,
            @ApiParam(value = "Client identification", required = true)
            @PathVariable("clientId")
                    Optional<String> clientId) throws NotFoundException {

        logRequest(httpServletRequest);
        logger.debug("Params:[clientId: {} ]", clientId);

        if (clientId.isEmpty()) {
            logger.error(CLIENT_WAS_EXPECTED);
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, CLIENT_WAS_EXPECTED
            );
        }

        logger.info("Requested default total reward points by client");
        var resultList = rewardPointsService.getRewardPointsByClientTotal(clientId.get());


        if (resultList.isEmpty()) {
            logger.error(POINTS_FOR_CLIENT_S_NOT_FOUND, clientId.get());
            throw new NotFoundException(getNotFoundMsg(clientId));
        }

        var response =
                getGenericRestResponse(resultList, API_V, HttpStatus.OK.toString(), "");

        if(logger.isDebugEnabled()){
            logResults(resultList.toString());
            logResponse(response);
        }

        return response;
    }

    private String getNotFoundMsg(Optional<String> clientId) {
        return "Rewards points for client: " + clientId + " not found";
    }

    @GetMapping(path = "clients/{clientId}/monthly", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get monthly reward points by client ID", notes = "Gets all applicable Rewards Points by client monthly", response = MonthlyRewardPointsResponse.class)
    public GenericRestResponse<MonthlyRewardPointsResponse> getRewardPointsByClientMonthly(
            HttpServletRequest httpServletRequest,
            @ApiParam(value = "Client identification", required = true)
            @PathVariable("clientId")
                    Optional<String> clientId) throws NotFoundException {

        logRequest(httpServletRequest);
        logger.debug("Params:[clientId: {}, period: monthly]", clientId);

        if (!clientId.isPresent()) {
            logger.error(CLIENT_WAS_EXPECTED);
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, CLIENT_WAS_EXPECTED
            );
        }

        logger.info("Requested monthly reward points by client");
        var resultList = rewardPointsService.getRewardPointsByClientMonthly(clientId.get());

        boolean noResultsFound = resultList == null || resultList.isEmpty();
        if (noResultsFound) {
            logger.error("Rewards points for client: {}  not found", clientId);
            throw new NotFoundException(getNotFoundMsg(clientId));
        }

        var response =
                getGenericRestResponse(resultList, API_V, HttpStatus.OK.toString(), "");

        if(logger.isDebugEnabled()){
            logResults(resultList.toString());
            logResponse(response);
        }
        return response;
    }

    @GetMapping(path = "clients/{clientId}/total", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get total reward points by client ID", notes = "Gets all applicable Rewards Points by client", response = MonthlyRewardPointsResponse.class)
    public GenericRestResponse<TotalRewardPointsResponse> getRewardPointsByClientTotally(
            HttpServletRequest httpServletRequest,
            @ApiParam(value = "Client identification", required = true)
            @PathVariable("clientId")
                    Optional<String> clientId) throws NotFoundException {

        logRequest(httpServletRequest);
        logger.debug("Params:[clientId: {}, period: total]", clientId);

        if (!clientId.isPresent()) {
            logger.error(CLIENT_WAS_EXPECTED);
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, CLIENT_WAS_EXPECTED
            );
        }

        logger.info("Requested default total reward points by client");
        var resultList = rewardPointsService.getRewardPointsByClientTotal(clientId.get());

        boolean noResultsFound = resultList == null || resultList.isEmpty();
        if (noResultsFound) {
            logger.error("Rewards points for client: {}  not found", clientId);
            throw new NotFoundException(getNotFoundMsg(clientId));
        }

        var response =
                getGenericRestResponse(resultList, API_V, HttpStatus.OK.toString(), "");

        if(logger.isDebugEnabled()){
            logResults(resultList.toString());
            logResponse(response);
        }
        return response;
    }

}
