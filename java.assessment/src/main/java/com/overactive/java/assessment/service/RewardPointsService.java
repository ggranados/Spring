package com.overactive.java.assessment.service;

import com.overactive.java.assessment.response.MonthlyRewardPointsResponse;
import com.overactive.java.assessment.response.TotalRewardPointsResponse;

import java.util.ArrayList;

public interface RewardPointsService {
    ArrayList<MonthlyRewardPointsResponse> getRewardPointsByClientMonthly(String clientId);

    ArrayList<TotalRewardPointsResponse> getAllRewardPoints();

    ArrayList<TotalRewardPointsResponse> getRewardPointsByClientTotal(String clientId);
}
