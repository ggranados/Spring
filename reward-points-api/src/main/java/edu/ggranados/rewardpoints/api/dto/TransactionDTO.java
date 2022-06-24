package edu.ggranados.rewardpoints.api.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

}
