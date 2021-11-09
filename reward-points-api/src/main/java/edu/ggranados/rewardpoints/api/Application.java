package edu.ggranados.rewardpoints.api;

import edu.ggranados.rewardpoints.api.entity.Transaction;
import edu.ggranados.rewardpoints.api.repository.TransactionRepository;
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

	public static final String CLI_001 = "CLI001";
	public static final String CLI_002 = "CLI002";
	public static final String CLI_003 = "CLI003";
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
				if ( logger.isDebugEnabled() ) {
					logger.info("Inserting test data...");
				}

				transactionRepository.save(new Transaction(CLI_001,BigDecimal.valueOf(120.00),getDate("01/09/2020"),true));
				transactionRepository.save(new Transaction(CLI_001,BigDecimal.valueOf(210.00),getDate("01/06/2021"),true));
				transactionRepository.save(new Transaction(CLI_001,BigDecimal.valueOf(150.00),getDate("01/05/2020"),true));
				transactionRepository.save(new Transaction(CLI_002,BigDecimal.valueOf(150.00),getDate("12/12/2020"),true));
				transactionRepository.save(new Transaction(CLI_003,BigDecimal.valueOf(230.00),getDate("01/11/2021"),true));
				transactionRepository.save(new Transaction(CLI_001,BigDecimal.valueOf(130.00),getDate("21/09/2020"),false));
				transactionRepository.save(new Transaction(CLI_001,BigDecimal.valueOf(260.00),getDate("15/02/2019"),true));
				transactionRepository.save(new Transaction(CLI_001,BigDecimal.valueOf(180.00),getDate("02/01/2020"),true));
				transactionRepository.save(new Transaction(CLI_002,BigDecimal.valueOf(150.00),getDate("06/07/2020"),true));
				transactionRepository.save(new Transaction(CLI_003,BigDecimal.valueOf(620.00),getDate("01/10/2021"),true));
				transactionRepository.save(new Transaction(CLI_001,BigDecimal.valueOf(125.00),getDate("04/09/2020"),true));
				transactionRepository.save(new Transaction(CLI_001,BigDecimal.valueOf(524.00),getDate("05/03/2020"),false));
				transactionRepository.save(new Transaction(CLI_001,BigDecimal.valueOf(672.00),getDate("20/08/2020"),true));
				transactionRepository.save(new Transaction(CLI_002,BigDecimal.valueOf(152.00),getDate("18/07/2021"),false));
				transactionRepository.save(new Transaction(CLI_003,BigDecimal.valueOf(233.00),getDate("13/09/2019"),true));

				if ( logger.isDebugEnabled() ) {
					logger.debug("Rewards Points Service configured, now waiting requests...");
				}
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
