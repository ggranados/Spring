package com.overactive.java.assessment.components;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component("onePointsCalculator")
@PropertySource("classpath:reward-points.properties")
@Getter @Setter
public class RewardPointsOnePointCalculator implements PointsCalculator{

    @Value("${onePointsCalculator.baseAmount}")
    private String onePointBaseAmount;

    @Override
    public Long calculate(BigDecimal amount){
        BigDecimal baseAmount = new BigDecimal(onePointBaseAmount);
        if(amount.compareTo(baseAmount) < 0)
            return 0L;
        return baseAmount
                .toBigInteger().longValue();
    }
}
