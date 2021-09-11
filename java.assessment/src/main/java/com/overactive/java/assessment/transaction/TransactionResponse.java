package com.overactive.java.assessment.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private Long id;
    private String clientId;
    private BigDecimal amount;
    private Date date;
    private Boolean applicable;

    public TransactionResponse(Transaction t){
        this.id = t.getId();
        this.clientId = t.getClientId();
        this.amount = t.getAmount();
        this.date = t.getDate();
        this.applicable = t.getApplicable();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionResponse that = (TransactionResponse) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
