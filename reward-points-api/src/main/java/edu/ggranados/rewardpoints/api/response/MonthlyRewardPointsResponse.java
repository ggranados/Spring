package edu.ggranados.rewardpoints.api.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Objects;

@Data
public class MonthlyRewardPointsResponse extends RewardPointsResponse{

    @ApiModelProperty(value = "Month name")
    private String month;

    public MonthlyRewardPointsResponse(Long points, String month) {
        super(points);
        this.month = month;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonthlyRewardPointsResponse that = (MonthlyRewardPointsResponse) o;
        return month.equals(that.month);
    }

    @Override
    public int hashCode() {
        return Objects.hash(month);
    }
}
