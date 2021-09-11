package com.overactive.java.assessment.rewardpoints;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class RewardPointsResponse {
    private String clientId;
    private Long points;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RewardPointsResponse that = (RewardPointsResponse) o;
        return clientId.equals(that.clientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId);
    }
}
