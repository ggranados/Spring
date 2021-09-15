package com.overactive.java.assessment;

import com.overactive.java.assessment.entity.Transaction;
import com.overactive.java.assessment.repository.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	@Configuration
	public class TransactionConfiguration {

		@Bean
		CommandLineRunner commandLineRunner(
				TransactionRepository transactionRepository){
			return args -> {
				transactionRepository.save(new Transaction(Long.valueOf(1000),"CLI001",new BigDecimal(120.00),getDate("01/09/2020"),true));
				transactionRepository.save(new Transaction(Long.valueOf(1001),"CLI001",new BigDecimal(200.00),getDate("01/08/2020"),true));
				transactionRepository.save(new Transaction(Long.valueOf(1002),"CLI001",new BigDecimal(100.00),getDate("01/08/2020"),true));
				transactionRepository.save(new Transaction(Long.valueOf(1003),"CLI002",new BigDecimal(150.00),getDate("01/09/2020"),true));
				transactionRepository.save(new Transaction(Long.valueOf(1004),"CLI003",new BigDecimal(230.00),getDate("01/07/2020"),true));
				transactionRepository.save(new Transaction(Long.valueOf(2000),"CLI001",new BigDecimal(120.00),getDate("01/09/2020"),false));
				transactionRepository.save(new Transaction(Long.valueOf(2001),"CLI001",new BigDecimal(200.00),getDate("01/08/2020"),true));
				transactionRepository.save(new Transaction(Long.valueOf(2002),"CLI001",new BigDecimal(100.00),getDate("01/08/2020"),true));
				transactionRepository.save(new Transaction(Long.valueOf(2003),"CLI002",new BigDecimal(150.00),getDate("01/07/2020"),true));
				transactionRepository.save(new Transaction(Long.valueOf(2004),"CLI003",new BigDecimal(230.00),getDate("01/09/2020"),true));
				transactionRepository.save(new Transaction(Long.valueOf(3000),"CLI001",new BigDecimal(120.00),getDate("01/09/2020"),true));
				transactionRepository.save(new Transaction(Long.valueOf(3001),"CLI001",new BigDecimal(200.00),getDate("01/07/2020"),false));
				transactionRepository.save(new Transaction(Long.valueOf(3002),"CLI001",new BigDecimal(100.00),getDate("01/08/2020"),true));
				transactionRepository.save(new Transaction(Long.valueOf(3003),"CLI002",new BigDecimal(150.00),getDate("01/07/2020"),false));
				transactionRepository.save(new Transaction(Long.valueOf(3004),"CLI003",new BigDecimal(230.00),getDate("01/09/2020"),true));
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

}
