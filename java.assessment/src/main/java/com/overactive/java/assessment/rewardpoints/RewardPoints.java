package com.overactive.java.assessment.rewardpoints;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class RewardPoints {
    private String clientId;
    private String month;
    private Long points;
    private BigDecimal amount;
}


