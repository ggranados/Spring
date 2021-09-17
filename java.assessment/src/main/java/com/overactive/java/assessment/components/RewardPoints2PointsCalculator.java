package com.overactive.java.assessment.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("_2PointsCalculator")
@PropertySource("classpath:rewardpoints.properties")
public class RewardPoints2PointsCalculator implements PointsCalculator{

    @Value("${_2PointsCalculator.baseAmount}")
    private String _2pointsBaseAmount;

    @Override
    public Long calculate(BigDecimal amount){
        BigDecimal baseAmount = new BigDecimal(_2pointsBaseAmount);
        if(amount.compareTo(baseAmount) <= 0)
            return 0L;
        return amount.subtract(baseAmount)
                .multiply(new BigDecimal(2))
                .toBigInteger().longValue();
    }
}
