package com.overactive.java.assessment.entity;

import lombok.*;

import javax.persistence.*;
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
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String clientId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private Boolean applicable;

}
