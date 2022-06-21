package edu.ggranados.java.algorithms.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {
		"edu.ggranados.java.algorithms.commands",
		"edu.ggranados.java.algorithms.sort",
		"edu.ggranados.java.algorithms.handlers"})
public class AlgorithmsApplication {

		public static void main(String[] args) {
			SpringApplication.run(AlgorithmsApplication.class, args);
		}
}


