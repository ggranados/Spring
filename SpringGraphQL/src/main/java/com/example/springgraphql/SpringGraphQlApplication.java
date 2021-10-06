package com.example.springgraphql;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	@SchemaMapping (typeName = "Customer")
	Flux<Order> orders (Customer customer){

		// Data Fetcher | Resolver | Handler Method
		var orders = Stream
				.iterate(0,x -> x+1).limit(10)
				.map(x-> new Order(Double.valueOf(Math.random()*100).intValue(),1))
				.collect(Collectors.toCollection(ArrayList::new));

		return Flux.fromIterable(orders);
	}

	@QueryMapping
	Flux <Customer> customers (){
		return this.repository.findAll();
	}

	@QueryMapping
	Flux <Customer> customersByName(@Argument String name){
		return repository.findByName(name);
	}
}

interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {

		Flux <Customer> findByName(String name);
}

record Order (Integer id, Integer customerId){

}

record Customer (
		@JsonProperty("cusId") @Id Integer id,
		@JsonProperty("cusName") String name){

}