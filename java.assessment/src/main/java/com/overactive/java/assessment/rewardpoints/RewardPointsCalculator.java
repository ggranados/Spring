package com.overactive.java.assessment.rewardpoints;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@PropertySource("classpath:rewardpoints.properties")
public class RewardPointsCalculator {

    @Value("${2points.baseAmount}")
    private String _2pointsBaseAmount;

    @Value("${1points.baseAmount}")
    private String _1pointsBaseAmount;

    public Long calculateBy2PointRule(BigDecimal amount){
        BigDecimal baseAmount = new BigDecimal(_2pointsBaseAmount);
        if(amount.compareTo(baseAmount) <= 0)
            return 0L;
        return amount.subtract(baseAmount)
                .multiply(new BigDecimal(2))
                .toBigInteger().longValue();
    }

    public Long calculateBy1PointRule(BigDecimal amount){
        BigDecimal baseAmount = new BigDecimal(_1pointsBaseAmount);
        if(amount.compareTo(baseAmount) < 0)
            return 0L;
        return baseAmount
                .toBigInteger().longValue();
    }
}
