package com.overactive.java.assessment.components;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("twoPointsCalculator")
@PropertySource("classpath:reward-points.properties")
@Getter @Setter
public class RewardPointsTwoPointsCalculator implements PointsCalculator{

    @Value("${twoPointsCalculator.baseAmount}")
    private String twoPointsBaseAmount;

    @Override
    public Long calculate(BigDecimal amount){
        BigDecimal baseAmount = new BigDecimal(twoPointsBaseAmount);
        if(amount.compareTo(baseAmount) <= 0)
            return 0L;
        return amount.subtract(baseAmount)
                .multiply(new BigDecimal(2))
                .toBigInteger().longValue();
    }
}
