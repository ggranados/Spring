package com.overactive.java.assessment.rewardpoints;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class MonthRewardPointsResponse {
    private String month;
    private Long points;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonthRewardPointsResponse that = (MonthRewardPointsResponse) o;
        return month.equals(that.month);
    }

    @Override
    public int hashCode() {
        return Objects.hash(month);
    }
}
