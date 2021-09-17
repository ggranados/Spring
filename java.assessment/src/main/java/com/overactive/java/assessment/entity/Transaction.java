package com.overactive.java.assessment.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Entity
@Table
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "clientId is mandatory")
    private String clientId;

    @Column(nullable = false)
    @NotNull(message = "amount is mandatory")
    private BigDecimal amount;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    @NotNull(message = "applicable is mandatory")
    private Boolean applicable;

    public Transaction(String clientId, BigDecimal amount, Date date, Boolean applicable) {
        this.clientId = clientId;
        this.amount = amount;
        this.date = date;
        this.applicable = applicable;
    }
}
