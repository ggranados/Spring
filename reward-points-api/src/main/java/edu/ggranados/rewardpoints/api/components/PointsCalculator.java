package edu.ggranados.rewardpoints.api.components;

import java.math.BigDecimal;

@FunctionalInterface
public interface PointsCalculator {
    Long calculate(BigDecimal amount);
}
