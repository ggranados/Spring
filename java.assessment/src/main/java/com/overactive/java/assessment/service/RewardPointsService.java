package com.overactive.java.assessment.service;

import com.overactive.java.assessment.components.RewardPoints2PointsCalculator;
import com.overactive.java.assessment.response.MonthlyRewardPointsResponse;
import com.overactive.java.assessment.response.TotalRewardPointsResponse;
import com.overactive.java.assessment.components.RewardPoints1PointCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.DateFormatSymbols;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RewardPointsService {

    private static final Logger logger = LoggerFactory.getLogger(RewardPointsService.class);

    private static TransactionService transactionService;
    private static RewardPoints1PointCalculator rewardPoints1PointCalculator;
    private static RewardPoints2PointsCalculator rewardPoints2PointCalculator;

    @Autowired
    public RewardPointsService(
            TransactionService transactionService,
            @Qualifier("_1PointsCalculator") RewardPoints1PointCalculator rewardPoints1PointCalculator,
            @Qualifier("_2PointsCalculator") RewardPoints2PointsCalculator rewardPoints2PointCalculator) {
        this.transactionService = transactionService;
        this.rewardPoints1PointCalculator = rewardPoints1PointCalculator;
        this.rewardPoints2PointCalculator = rewardPoints2PointCalculator;
    }

    public ArrayList<MonthlyRewardPointsResponse> getRewardPointsByClientMonthly(String clientId) {
        logger.debug("Getting reward points by client " + clientId + " monthly");
        return transactionService.findAllApplicableTransactionsByClient(clientId)
                .stream()
                .map(t->{
                    String month = getMonthFromDate(t.getDate());
                    Long points = rewardPoints1PointCalculator.calculate(t.getAmount());
                    points += rewardPoints1PointCalculator.calculate(t.getAmount());
                    return new MonthlyRewardPointsResponse(points,month);
                })
                .collect(
                        Collectors.groupingBy(
                                foo -> foo.getMonth(),
                                Collectors.summingLong(foo->foo.getPoints())
                        )
                )
                .entrySet().stream().map( e-> new MonthlyRewardPointsResponse(e.getValue(),e.getKey()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<TotalRewardPointsResponse> getAllRewardPoints() {
        logger.debug("Getting all reward points by client");
        return transactionService.findAll()
                .stream()
                .map(t->{
                    String month = getMonthFromDate(t.getDate());
                    Long points = rewardPoints2PointCalculator.calculate(t.getAmount());
                    points += rewardPoints1PointCalculator.calculate(t.getAmount());
                    return new TotalRewardPointsResponse(points,t.getClientId());
                })
                .collect(
                        Collectors.groupingBy(
                                foo -> foo.getClientId(),
                                Collectors.summingLong(foo->foo.getPoints())
                        )
                )
                .entrySet().stream().map( e-> new TotalRewardPointsResponse(e.getValue(),e.getKey()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<TotalRewardPointsResponse> getRewardPointsByClientTotal(String clientId) {
        logger.debug("Getting reward points by client " + clientId + " total");
        return transactionService.findAllApplicableTransactionsByClient(clientId)
                .stream()
                .map(t->{
                    Long points = rewardPoints2PointCalculator.calculate(t.getAmount());
                    points += rewardPoints1PointCalculator.calculate(t.getAmount());
                    return new TotalRewardPointsResponse(points,t.getClientId());
                })
                .collect(
                        Collectors.groupingBy(
                                foo -> foo.getClientId(),
                                Collectors.summingLong(foo->foo.getPoints())
                        )
                )
                .entrySet().stream().map( e-> new TotalRewardPointsResponse(e.getValue(),e.getKey()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private String getMonthFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getMonthForInt(calendar.get(Calendar.MONTH));
    }

    private String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }

}
