package edu.ggranados.rewardpoints.api.service;

import edu.ggranados.rewardpoints.api.components.RewardPointsTwoPointsCalculator;
import edu.ggranados.rewardpoints.api.response.MonthlyRewardPointsResponse;
import edu.ggranados.rewardpoints.api.response.TotalRewardPointsResponse;
import edu.ggranados.rewardpoints.api.components.RewardPointsOnePointCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.text.DateFormatSymbols;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RewardPointsServiceImpl implements RewardPointsService {

    private static final Logger logger = LoggerFactory.getLogger(RewardPointsServiceImpl.class);

    private final TransactionServiceImpl transactionService;
    private final RewardPointsOnePointCalculator rewardPointsOnePointCalculator;
    private final RewardPointsTwoPointsCalculator rewardPointsTwoPointCalculator;

    @Autowired
    public RewardPointsServiceImpl(
            TransactionServiceImpl transactionService,
            @Qualifier("onePointsCalculator") RewardPointsOnePointCalculator rewardPoints1PointCalculator,
            @Qualifier("twoPointsCalculator") RewardPointsTwoPointsCalculator rewardPoints2PointCalculator) {
        this.transactionService = transactionService;
        this.rewardPointsOnePointCalculator = rewardPoints1PointCalculator;
        this.rewardPointsTwoPointCalculator = rewardPoints2PointCalculator;
    }

    @Override
    public ArrayList<MonthlyRewardPointsResponse> getRewardPointsByClientMonthly(String clientId) {
        logger.debug("Getting reward points by client {}", clientId);
        return transactionService.findAllApplicableTransactionsByClient(clientId)
                .stream()
                .map(t->{
                    String month = getMonthFromDate(t.getDate());
                    Long points = rewardPointsOnePointCalculator.calculate(t.getAmount());
                    points += rewardPointsTwoPointCalculator.calculate(t.getAmount());
                    return new MonthlyRewardPointsResponse(points,month);
                })
                .collect(
                        Collectors.groupingBy(
                                MonthlyRewardPointsResponse::getMonth,
                                Collectors.summingLong(MonthlyRewardPointsResponse::getPoints)
                        )
                )
                .entrySet().stream().map( e-> new MonthlyRewardPointsResponse(e.getValue(),e.getKey()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<TotalRewardPointsResponse> getAllRewardPoints() {
        logger.debug("Getting all reward points by client");
        return transactionService.findAll()
                .stream()
                .map(t->{
                    Long points = rewardPointsTwoPointCalculator.calculate(t.getAmount());
                    points += rewardPointsOnePointCalculator.calculate(t.getAmount());
                    return new TotalRewardPointsResponse(points,t.getClientId());
                })
                .collect(
                        Collectors.groupingBy(
                                TotalRewardPointsResponse::getClientId,
                                Collectors.summingLong(TotalRewardPointsResponse::getPoints)
                        )
                )
                .entrySet().stream().map( e-> new TotalRewardPointsResponse(e.getValue(),e.getKey()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<TotalRewardPointsResponse> getRewardPointsByClientTotal(String clientId) {
        logger.debug("Getting reward points by client {} total", clientId);
        return transactionService.findAllApplicableTransactionsByClient(clientId)
                .stream()
                .map(t->{
                    Long points = rewardPointsTwoPointCalculator.calculate(t.getAmount());
                    points += rewardPointsOnePointCalculator.calculate(t.getAmount());
                    return new TotalRewardPointsResponse(points,t.getClientId());
                })
                .collect(
                        Collectors.groupingBy(
                                TotalRewardPointsResponse::getClientId,
                                Collectors.summingLong(TotalRewardPointsResponse::getPoints)
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
