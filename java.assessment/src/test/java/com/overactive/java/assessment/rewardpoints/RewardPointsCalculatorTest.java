package com.overactive.java.assessment.rewardpoints;

import com.overactive.java.assessment.components.RewardPoints1PointCalculator;
import com.overactive.java.assessment.components.RewardPoints2PointsCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class RewardPointsCalculatorTest {

    @Autowired
    @Qualifier("_1PointsCalculator")
    private RewardPoints1PointCalculator rewardPoints1PointCalculator;

    @Autowired
    @Qualifier("_2PointsCalculator")
    private RewardPoints2PointsCalculator rewardPoints2PointsCalculator;

    @Test
    @DisplayName("Testing expected 2 points for every dollar spent over $100 in each transaction")
    void calculateBy2PointRule() {
        assertAll(
                ()-> assertEquals(rewardPoints2PointsCalculator.calculate(BigDecimal.ZERO),0L, ()-> "0 points expected to be calculated for amount of 0"),
                ()-> assertEquals(rewardPoints2PointsCalculator.calculate(new BigDecimal(50)),0L,()-> "0 points expected to be calculated  for amount of 50"),
                ()-> assertEquals(rewardPoints2PointsCalculator.calculate(new BigDecimal(100)),0L, ()-> "0 points expected to be calculated for amount of 100"),
                ()-> assertEquals(rewardPoints2PointsCalculator.calculate(new BigDecimal(120)),40L, ()-> "40 points to be calculated fo amount of 120")
        );

    }

    @Test
    @DisplayName("Testing expected 1 point for every dollar spent over $50 in each transaction")
    void calculateBy1PointRule() {
        assertAll(
                ()-> assertEquals(rewardPoints1PointCalculator.calculate(BigDecimal.ZERO),0L, ()-> "0 points expected to be calculated for amount of 0"),
                ()-> assertEquals(rewardPoints1PointCalculator.calculate(new BigDecimal(50)),50L,()-> "50 points expected to be calculated for amount of 50"),
                ()-> assertEquals(rewardPoints1PointCalculator.calculate(new BigDecimal(100)),50L,()-> "50 points expected to be calculated for amount of 100"),
                ()-> assertEquals(rewardPoints1PointCalculator.calculate(new BigDecimal(120)),50L,()-> "50 points expected to be calculated for amount of 120")
        );
    }
}