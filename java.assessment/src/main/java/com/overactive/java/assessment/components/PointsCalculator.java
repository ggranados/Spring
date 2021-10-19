package com.overactive.java.assessment.components;

import java.math.BigDecimal;

@FunctionalInterface
public interface PointsCalculator {
    Long calculate(BigDecimal amount);
}
