package edu.ggranados.rewardpoints.api.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RewardPointsResponse {

    @ApiModelProperty(value = "Reward points amount")
    private Long points;

}
