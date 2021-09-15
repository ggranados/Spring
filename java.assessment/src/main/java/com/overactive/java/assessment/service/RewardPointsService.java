package com.overactive.java.assessment.service;

import com.overactive.java.assessment.response.MonthRewardPointsResponse;
import com.overactive.java.assessment.response.RewardPointsResponse;
import com.overactive.java.assessment.util.RewardPointsCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

    public List<MonthRewardPointsResponse> getRewardPointsByClientMonthly(String clientId) {
        return transactionService.findAllApplicableTransactionsByClient(clientId)
                .stream()
                .map(t->{
                    String month = getMonthFromDate(t.getDate());
                    Long points = rewardPointsCalculator.calculateBy2PointRule(t.getAmount());
                    points += rewardPointsCalculator.calculateBy1PointRule(t.getAmount());
                    return new MonthRewardPointsResponse(month,points);
                })
                .collect(
                        Collectors.groupingBy(
                                foo -> foo.getMonth(),
                                Collectors.summingLong(foo->foo.getPoints())
                        )
                )
                .entrySet().stream().map( e-> new MonthRewardPointsResponse(e.getKey(),e.getValue()))
                .collect(Collectors.toList());
    }

    public List<RewardPointsResponse> getAllRewardPoints() {
        return transactionService.findAll()
                .stream()
                .map(t->{
                    String month = getMonthFromDate(t.getDate());
                    Long points = rewardPointsCalculator.calculateBy2PointRule(t.getAmount());
                    points += rewardPointsCalculator.calculateBy1PointRule(t.getAmount());
                    return new RewardPointsResponse(t.getClientId(),points);
                })
                .collect(
                        Collectors.groupingBy(
                                foo -> foo.getClientId(),
                                Collectors.summingLong(foo->foo.getPoints())
                        )
                )
                .entrySet().stream().map( e-> new RewardPointsResponse(e.getKey(),e.getValue()))
                .collect(Collectors.toList());
    }

    public List<RewardPointsResponse> getRewardPointsByClientTotal(String clientId) {
        return transactionService.findAllApplicableTransactionsByClient(clientId)
                .stream()
                .map(t->{
                    Long points = rewardPointsCalculator.calculateBy2PointRule(t.getAmount());
                    points += rewardPointsCalculator.calculateBy1PointRule(t.getAmount());
                    return new RewardPointsResponse(t.getClientId(),points);
                })
                .collect(
                        Collectors.groupingBy(
                                foo -> foo.getClientId(),
                                Collectors.summingLong(foo->foo.getPoints())
                        )
                )
                .entrySet().stream().map( e-> new RewardPointsResponse(e.getKey(),e.getValue()))
                .collect(Collectors.toList());
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
