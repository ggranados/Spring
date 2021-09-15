package com.overactive.java.assessment.rewardpoints;

import com.overactive.java.assessment.util.RewardPointsCalculator;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class RewardPointsCalculatorTest {

    @Autowired
    private RewardPointsCalculator rewardPointsCalculator;

    @Test
    void calculateBy2PointRule() {
        Long points = rewardPointsCalculator.calculateBy2PointRule(BigDecimal.ZERO);
        assertEquals(points,0L);

        points = rewardPointsCalculator.calculateBy2PointRule(new BigDecimal(50));
        assertEquals(points,0L);

        points = rewardPointsCalculator.calculateBy2PointRule(new BigDecimal(100));
        assertEquals(points,0L);

        points = rewardPointsCalculator.calculateBy2PointRule(new BigDecimal(120));
        assertEquals(points,40L);
    }

    @Test
    void calculateBy1PointRule() {
        Long points = rewardPointsCalculator.calculateBy1PointRule(BigDecimal.ZERO);
        assertEquals(points,0L);

        points = rewardPointsCalculator.calculateBy1PointRule(new BigDecimal(50));
        assertEquals(points,50L);

        points = rewardPointsCalculator.calculateBy1PointRule(new BigDecimal(100));
        assertEquals(points,50L);

        points = rewardPointsCalculator.calculateBy1PointRule(new BigDecimal(120));
        assertEquals(points,50L);
    }
}