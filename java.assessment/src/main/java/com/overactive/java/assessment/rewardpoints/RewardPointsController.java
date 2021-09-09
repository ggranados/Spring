package com.overactive.java.assessment.rewardpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/rewardspoints")
public class RewardPointsController {

    private final RewardPointsService rewardPointsService;

    @Autowired
    public RewardPointsController(RewardPointsService rewardPointsService){
        this.rewardPointsService = rewardPointsService;
    }

    @GetMapping(path = "{clientId}/monthly")
    public Map getRewardPointsByClientMonthly(@PathVariable("clientId") String clientId){
        return rewardPointsService.getRewardPointsByClientMonthly(clientId);
    }

    @GetMapping(path = "clients/all")
    public Map getAllRewardPoints(){
        return rewardPointsService.getAllRewardPoints();
    }

    @GetMapping(path = "{clientId}/total")
    public Map getRewardPointsByClientTotal(@PathVariable("clientId") String clientId){
        return rewardPointsService.getRewardPointsByClientTotal(clientId);
    }

}
