package com.overactive.java.assessment.components;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("_1PointsCalculator")
@PropertySource("classpath:rewardpoints.properties")
@Getter @Setter
public class RewardPoints1PointsCalculator implements PointsCalculator{

    @Value("${_1PointsCalculator.baseAmount}")
    private String _1pointsBaseAmount;

    @Override
    public Long calculate(BigDecimal amount){
        BigDecimal baseAmount = new BigDecimal(_1pointsBaseAmount);
        if(amount.compareTo(baseAmount) < 0)
            return 0L;
        return baseAmount
                .toBigInteger().longValue();
    }
}
