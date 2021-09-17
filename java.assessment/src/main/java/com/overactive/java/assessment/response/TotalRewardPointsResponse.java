package com.overactive.java.assessment.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
public class TotalRewardPointsResponse extends RewardPointsResponse{
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
