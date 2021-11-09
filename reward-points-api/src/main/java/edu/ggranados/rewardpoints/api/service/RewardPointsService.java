package edu.ggranados.rewardpoints.api.service;

import edu.ggranados.rewardpoints.api.response.MonthlyRewardPointsResponse;
import edu.ggranados.rewardpoints.api.response.TotalRewardPointsResponse;
import java.util.ArrayList;

public interface RewardPointsService {
    ArrayList<MonthlyRewardPointsResponse> getRewardPointsByClientMonthly(String clientId);

    ArrayList<TotalRewardPointsResponse> getAllRewardPoints();

    ArrayList<TotalRewardPointsResponse> getRewardPointsByClientTotal(String clientId);
}
