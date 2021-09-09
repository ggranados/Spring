package com.overactive.java.assessment.transaction;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class TransactionConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(
            TransactionRepository transactionRepository){
        return args -> {
            transactionRepository.save(new Transaction(Long.valueOf(1000),"CLI001",new BigDecimal(120.00),getDate("01/09/2020")));
            transactionRepository.save(new Transaction(Long.valueOf(1001),"CLI001",new BigDecimal(200.00),getDate("01/08/2020")));
            transactionRepository.save(new Transaction(Long.valueOf(1002),"CLI001",new BigDecimal(100.00),getDate("01/08/2020")));
            transactionRepository.save(new Transaction(Long.valueOf(1003),"CLI002",new BigDecimal(150.00),getDate("01/09/2020")));
            transactionRepository.save(new Transaction(Long.valueOf(1004),"CLI003",new BigDecimal(230.00),getDate("01/09/2020")));
        };
    }

    private Date getDate(String input) {
        SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return parser.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
