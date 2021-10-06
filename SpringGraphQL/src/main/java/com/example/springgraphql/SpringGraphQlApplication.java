package com.example.springgraphql;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;

@SpringBootApplication
public class SpringGraphQlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringGraphQlApplication.class, args);

		// Example for using record
		var customer = new Customer(1,"Guillermo");
		var id = customer.id();
		var name = customer.name();
	}

}


record Customer (
		@JsonProperty("cusId") @Id Integer id,
		@JsonProperty("cusName") String name){

}