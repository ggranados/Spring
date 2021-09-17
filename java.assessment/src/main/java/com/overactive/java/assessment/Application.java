package com.overactive.java.assessment;

import com.overactive.java.assessment.entity.Transaction;
import com.overactive.java.assessment.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@EnableAspectJAutoProxy
public class Application {

	private static Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Configuration
	public class TransactionConfiguration {

		@Bean
		CommandLineRunner commandLineRunner(
				TransactionRepository transactionRepository){
			logger.info("Rewards Points Service started");
			return args -> {
				logger.info("Inserting test data...");
				logger.debug(transactionRepository.save(new Transaction("CLI001",new BigDecimal(120.00),getDate("01/09/2020"),true)).toString());
				logger.debug(transactionRepository.save(new Transaction("CLI001",new BigDecimal(200.00),getDate("01/08/2020"),true)).toString());
				logger.debug(transactionRepository.save(new Transaction("CLI001",new BigDecimal(100.00),getDate("01/08/2020"),true)).toString());;
				logger.debug(transactionRepository.save(new Transaction("CLI002",new BigDecimal(150.00),getDate("01/09/2020"),true)).toString());;
				logger.debug(transactionRepository.save(new Transaction("CLI003",new BigDecimal(230.00),getDate("01/07/2020"),true)).toString());;
				logger.debug(transactionRepository.save(new Transaction("CLI001",new BigDecimal(120.00),getDate("01/09/2020"),false)).toString());;
				logger.debug(transactionRepository.save(new Transaction("CLI001",new BigDecimal(200.00),getDate("01/08/2020"),true)).toString());;
				logger.debug(transactionRepository.save(new Transaction("CLI001",new BigDecimal(100.00),getDate("01/08/2020"),true)).toString());;
				logger.debug(transactionRepository.save(new Transaction("CLI002",new BigDecimal(150.00),getDate("01/07/2020"),true)).toString());;
				logger.debug(transactionRepository.save(new Transaction("CLI003",new BigDecimal(230.00),getDate("01/09/2020"),true)).toString());;
				logger.debug(transactionRepository.save(new Transaction("CLI001",new BigDecimal(120.00),getDate("01/09/2020"),true)).toString());;
				logger.debug(transactionRepository.save(new Transaction("CLI001",new BigDecimal(200.00),getDate("01/07/2020"),false)).toString());;
				logger.debug(transactionRepository.save(new Transaction("CLI001",new BigDecimal(100.00),getDate("01/08/2020"),true)).toString());;
				logger.debug(transactionRepository.save(new Transaction("CLI002",new BigDecimal(150.00),getDate("01/07/2020"),false)).toString());;
				logger.debug(transactionRepository.save(new Transaction("CLI003",new BigDecimal(230.00),getDate("01/09/2020"),true)).toString());;

				logger.info("Rewards Points Service configured, now waiting requests...");
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
