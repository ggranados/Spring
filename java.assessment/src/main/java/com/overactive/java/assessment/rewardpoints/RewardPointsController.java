package com.overactive.java.assessment.rewardpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/reward-points")
public class RewardPointsController {

    private final RewardPointsService rewardPointsService;

    @Autowired
    public RewardPointsController(RewardPointsService rewardPointsService){
        this.rewardPointsService = rewardPointsService;
    }

    @GetMapping(path = "{clientId}/monthly")
    public List<MonthRewardPointsResponse> getRewardPointsByClientMonthly(@PathVariable("clientId") String clientId){

        if(clientId == null || clientId.isBlank()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid client ID"
            );
        }

        List<MonthRewardPointsResponse>
                response = rewardPointsService.getRewardPointsByClientMonthly(clientId);

        if(response.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Rewards points for client: " + clientId +" not found"
            );
        }
        return response;
    }

    @GetMapping(path = "clients/all")
    public List<RewardPointsResponse> getAllRewardPoints(){

        List<RewardPointsResponse>
            response = rewardPointsService.getAllRewardPoints();

        if(response.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Rewards points for clients not found"
            );
        }
        return response;
    }

    @GetMapping(path = "{clientId}/total")
    public List<RewardPointsResponse> getRewardPointsByClientTotal(@PathVariable("clientId") String clientId){

        if(clientId == null || clientId.isBlank()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid client ID"
            );
        }

        List<RewardPointsResponse>
                response = rewardPointsService.getRewardPointsByClientTotal(clientId);

        if(response.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Rewards points for client: " + clientId +" not found"
            );
        }
        return response;
    }

}
