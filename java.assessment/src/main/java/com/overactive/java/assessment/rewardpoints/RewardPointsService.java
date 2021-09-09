package com.overactive.java.assessment.rewardpoints;

import com.overactive.java.assessment.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormatSymbols;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RewardPointsService {

    private static TransactionService transactionService;
    private static RewardPointsCalculator rewardPointsCalculator;

    @Autowired
    public RewardPointsService(
            TransactionService transactionService,
            RewardPointsCalculator rewardPointsCalculator) {
        this.transactionService = transactionService;
        this.rewardPointsCalculator = rewardPointsCalculator;
    }

    public Map getRewardPointsByClientMonthly(String clientId) {
        return transactionService.findAllByClient(clientId)
                .stream()
                .map(t->{
                    String month = getMonthFromDate(t.getDate());
                    Long points = rewardPointsCalculator.calculateBy2PointRule(t.getAmount());
                    points += rewardPointsCalculator.calculateBy1PointRule(t.getAmount());
                    return new RewardPoints(t.getClientId(),month,points,t.getAmount());
                })
                .collect(Collectors.groupingBy(foo -> foo.getMonth(),
                        Collectors.summingLong(foo->foo.getPoints())));
    }

    public Map getAllRewardPoints() {
        return transactionService.findAll()
                .stream()
                .map(t->{
                    String month = getMonthFromDate(t.getDate());
                    Long points = rewardPointsCalculator.calculateBy2PointRule(t.getAmount());
                    points += rewardPointsCalculator.calculateBy1PointRule(t.getAmount());
                    return new RewardPoints(t.getClientId(),month,points,t.getAmount());
                })
                .collect(Collectors.groupingBy(foo -> foo.getClientId(),
                        Collectors.summingLong(foo->foo.getPoints())));
    }

    public Map getRewardPointsByClientTotal(String clientId) {
        return transactionService.findAllByClient(clientId)
                .stream()
                .map(t->{
                    Long points = rewardPointsCalculator.calculateBy2PointRule(t.getAmount());
                    points += rewardPointsCalculator.calculateBy1PointRule(t.getAmount());
                    return new RewardPoints(t.getClientId(),null,points,t.getAmount());
                })
                .collect(Collectors.groupingBy(foo -> foo.getClientId(),
                        Collectors.summingLong(foo->foo.getPoints())));
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
