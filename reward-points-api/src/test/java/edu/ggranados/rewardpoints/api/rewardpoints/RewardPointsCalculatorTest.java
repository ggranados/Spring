package edu.ggranados.rewardpoints.api.rewardpoints;

import edu.ggranados.rewardpoints.api.components.RewardPointsOnePointCalculator;
import edu.ggranados.rewardpoints.api.components.RewardPointsTwoPointsCalculator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RewardPointsCalculatorTest {

    private static RewardPointsOnePointCalculator rewardPoints1PointsCalculator;
    private static RewardPointsTwoPointsCalculator rewardPoints2PointsCalculator;

    @BeforeAll
    private static void setUp(){
        rewardPoints1PointsCalculator = new RewardPointsOnePointCalculator();
        rewardPoints1PointsCalculator.setOnePointBaseAmount("50");
        rewardPoints2PointsCalculator = new RewardPointsTwoPointsCalculator();
        rewardPoints2PointsCalculator.setTwoPointsBaseAmount("100");
    }

    @Test
    @DisplayName("Testing expected 1 point for every dollar spent over $50 in each transaction")
    void calculateBy1PointRule() {
        assertAll(
                ()-> assertEquals(0L,rewardPoints1PointsCalculator.calculate(BigDecimal.ZERO), ()-> "0 points expected to be calculated for amount of 0"),
                ()-> assertEquals(50L,rewardPoints1PointsCalculator.calculate(new BigDecimal(50)),()-> "50 points expected to be calculated for amount of 50"),
                ()-> assertEquals(50L,rewardPoints1PointsCalculator.calculate(new BigDecimal(100)),()-> "50 points expected to be calculated for amount of 100"),
                ()-> assertEquals(50L,rewardPoints1PointsCalculator.calculate(new BigDecimal(120)),()-> "50 points expected to be calculated for amount of 120")
        );
    }

    @Test
    @DisplayName("Testing expected 2 points for every dollar spent over $100 in each transaction")
    void calculateBy2PointRule() {
        assertAll(
                ()-> assertEquals(0L,rewardPoints2PointsCalculator.calculate(BigDecimal.ZERO), ()-> "0 points expected to be calculated for amount of 0"),
                ()-> assertEquals(0L,rewardPoints2PointsCalculator.calculate(new BigDecimal(50)),()-> "0 points expected to be calculated  for amount of 50"),
                ()-> assertEquals(0L,rewardPoints2PointsCalculator.calculate(new BigDecimal(100)), ()-> "0 points expected to be calculated for amount of 100"),
                ()-> assertEquals(40L,rewardPoints2PointsCalculator.calculate(new BigDecimal(120)), ()-> "40 points to be calculated fo amount of 120")
        );
    }

}