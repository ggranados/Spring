package edu.ggranados.rewardpoints.api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class RewardPointsDTO {
    private String clientId;
    private String month;
    private Long points;
    private BigDecimal amount;
}


