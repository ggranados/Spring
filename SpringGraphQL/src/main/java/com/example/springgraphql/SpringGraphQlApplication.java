package com.example.springgraphql;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringGraphQlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringGraphQlApplication.class, args);

	}

}
@Controller
class CustomerGraphqlController{

	private final CustomerRepository repository;

	public CustomerGraphqlController(CustomerRepository repository) {
		this.repository = repository;
	}

	@QueryMapping
	Flux<Customer> customers (){
		return this.repository.findAll();
	}
}

interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {

}

record Customer (
		@JsonProperty("cusId") @Id Integer id,
		@JsonProperty("cusName") String name){

}