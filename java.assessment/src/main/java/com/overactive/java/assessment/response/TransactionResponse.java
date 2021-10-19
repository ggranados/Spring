package com.overactive.java.assessment.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionResponse {
    @ApiModelProperty(value = "Transaction ID")
    protected Long id;
    @ApiModelProperty(value = "Transaction amount")
    protected BigDecimal amount;
}
