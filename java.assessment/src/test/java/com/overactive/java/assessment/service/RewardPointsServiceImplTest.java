package com.overactive.java.assessment.service;

import com.overactive.java.assessment.components.RewardPoints1PointsCalculator;
import com.overactive.java.assessment.components.RewardPoints2PointsCalculator;

import com.overactive.java.assessment.response.MonthlyRewardPointsResponse;
import com.overactive.java.assessment.response.TotalRewardPointsResponse;
import com.overactive.java.assessment.response.TransactionResponseForRewards;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static com.overactive.java.assessment.service.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;


@ExtendWith(MockitoExtension.class)
class RewardPointsServiceImplTest {

    @Mock
    TransactionServiceImpl tranService;

    @Mock
    RewardPoints1PointsCalculator _1point;

    @Mock
    RewardPoints2PointsCalculator _2point;

    @InjectMocks
    RewardPointsServiceImpl service;


    @Test
    void getRewardPointsByClientMonthly() {
        ArrayList<TransactionResponseForRewards> data = new ArrayList<>();
        data.add(new TransactionResponseForRewards(trxApplicableForBoth));

        when(tranService.findAllApplicableTransactionsByClient("CLI001")).thenReturn(data);
        when(_1point.calculate(trxApplicableForBoth.getAmount())).thenReturn(50L);
        when(_2point.calculate(trxApplicableForBoth.getAmount())).thenReturn(40L);

        ArrayList<MonthlyRewardPointsResponse> result = service.getRewardPointsByClientMonthly("CLI001");

        verify(_1point, times(1)).calculate(trxApplicableForBoth.getAmount());
        verify(_2point, times(1)).calculate(trxApplicableForBoth.getAmount());

        assertAll(
                ()-> assertNotNull(result, ()-> "result should not be null"),
                ()-> assertFalse(result.isEmpty(), ()-> "result should not be empty"),
                ()-> assertTrue(result instanceof ArrayList,()-> "Expected ArrayList result type"),
                ()-> assertTrue(result.get(0) instanceof MonthlyRewardPointsResponse,()-> "Expected elements of MonthlyRewardPointsResponse in result list")
        );
    }

    @Test
    @DisplayName("Service should return a list of Rewards Points with all applicable results")
    void getAllRewardPoints() {
        ArrayList<TransactionResponseForRewards> data = new ArrayList<>();
        data.add(new TransactionResponseForRewards(trxApplicableForBoth));

        when(tranService.findAll()).thenReturn(data);
        when(_1point.calculate(trxApplicableForBoth.getAmount())).thenReturn(50L);
        when(_2point.calculate(trxApplicableForBoth.getAmount())).thenReturn(40L);

        ArrayList<TotalRewardPointsResponse> result = service.getAllRewardPoints();

        verify(_1point, times(1)).calculate(trxApplicableForBoth.getAmount());
        verify(_2point, times(1)).calculate(trxApplicableForBoth.getAmount());

        assertAll(
                ()-> assertNotNull(result, ()-> "result should not be null"),
                ()-> assertFalse(result.isEmpty(), ()-> "result should not be empty"),
                ()-> assertTrue(result instanceof ArrayList,()-> "Expected ArrayList result type"),
                ()-> assertTrue(result.get(0) instanceof TotalRewardPointsResponse,()-> "Expected elements of TotalRewardPointsResponse in result list")
        );
    }

    @Test
    @DisplayName("Service should return a list of Rewards Points with results for CLI001 and 90 points")
    void getRewardPointsByClientTotal() {
        ArrayList<TransactionResponseForRewards> data = new ArrayList<>();
        data.add(new TransactionResponseForRewards(trxApplicableForBoth));

        when(tranService.findAllApplicableTransactionsByClient("CLI001")).thenReturn(data);
        when(_1point.calculate(trxApplicableForBoth.getAmount())).thenReturn(50L);
        when(_2point.calculate(trxApplicableForBoth.getAmount())).thenReturn(40L);

        ArrayList<TotalRewardPointsResponse> result = service.getRewardPointsByClientTotal("CLI001");

        verify(_1point, times(1)).calculate(trxApplicableForBoth.getAmount());
        verify(_2point, times(1)).calculate(trxApplicableForBoth.getAmount());

        assertAll(
                ()-> assertNotNull(result, ()-> "result should not be null"),
                ()-> assertFalse(result.isEmpty(), ()-> "result should not be empty"),
                ()-> assertTrue(result instanceof ArrayList,()-> "Expected ArrayList result type"),
                ()-> assertTrue(result.get(0) instanceof TotalRewardPointsResponse,()-> "Expected elements of TotalRewardPointsResponse in result list")
        );
    }
}