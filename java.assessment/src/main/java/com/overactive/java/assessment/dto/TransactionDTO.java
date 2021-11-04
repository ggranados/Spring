package com.overactive.java.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class TransactionDTO {

    private Long id;

    @NotBlank(message = "clientId is mandatory")
    private String clientId;

    @NotNull(message = "amount is mandatory")
    private BigDecimal amount;

    private Date date;

    @NotNull(message = "applicable is mandatory")
    private Boolean applicable;

    public TransactionDTO(String clientId, BigDecimal amount, Date date, Boolean applicable) {
        this.clientId = clientId;
        this.amount = amount;
        this.date = date;
        this.applicable = applicable;
    }
}
