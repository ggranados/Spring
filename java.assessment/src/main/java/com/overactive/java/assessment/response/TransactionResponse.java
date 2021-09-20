package com.overactive.java.assessment.response;

import com.overactive.java.assessment.entity.Transaction;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "Transaction ID")
    private Long id;
    @ApiModelProperty(value = "Client Identification")
    private String clientId;
    @ApiModelProperty(value = "Transaction amount")
    private BigDecimal amount;
    @ApiModelProperty(value = "Transaction entering date")
    private Date date;
    @ApiModelProperty(value = "Reward points applicable flag")
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
