package com.overactive.java.assessment.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class TransactionPage {

    private int pageNumber = 0;
    private int pageSize = 10;
    private Sort.Direction sorDirection = Sort.Direction.ASC;
    private String sortBy = "clientId";


}