package edu.ggranados.rewardpoints.api.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Objects;

@Data
public class TotalRewardPointsResponse extends RewardPointsResponse{

    @ApiModelProperty(value = "Client's name")
    private String clientId;

    public TotalRewardPointsResponse(Long points, String clientId) {
        super(points);
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TotalRewardPointsResponse that = (TotalRewardPointsResponse) o;
        return clientId.equals(that.clientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId);
    }
}
