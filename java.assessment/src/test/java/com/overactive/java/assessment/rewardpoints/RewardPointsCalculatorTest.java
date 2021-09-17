package com.overactive.java.assessment.rewardpoints;

import com.overactive.java.assessment.components.RewardPoints1PointCalculator;
import com.overactive.java.assessment.components.RewardPoints2PointsCalculator;
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
    void calculateBy2PointRule() {
        Long points = rewardPoints2PointsCalculator.calculate(BigDecimal.ZERO);
        assertEquals(points,0L);

        points = rewardPoints2PointsCalculator.calculate(new BigDecimal(50));
        assertEquals(points,0L);

        points = rewardPoints2PointsCalculator.calculate(new BigDecimal(100));
        assertEquals(points,0L);

        points = rewardPoints2PointsCalculator.calculate(new BigDecimal(120));
        assertEquals(points,40L);
    }

    @Test
    void calculateBy1PointRule() {
        Long points = rewardPoints1PointCalculator.calculate(BigDecimal.ZERO);
        assertEquals(points,0L);

        points = rewardPoints1PointCalculator.calculate(new BigDecimal(50));
        assertEquals(points,50L);

        points = rewardPoints1PointCalculator.calculate(new BigDecimal(100));
        assertEquals(points,50L);

        points = rewardPoints1PointCalculator.calculate(new BigDecimal(120));
        assertEquals(points,50L);
    }
}