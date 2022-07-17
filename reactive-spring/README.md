# Reactive Spring Application

Simple reactive REST app with Spring Webblux and Embedded Reactive Mongo

## Instructions

Run SpringApp and trigger REST requests through Postman

- POST http://localhost:8080/room/v1/reservation/

````
{
	"roomNumber" : 345,
	"checkIn" : "2019-05-03",
	"checkOut" : "2019-05-09",
	"price" : 210
}
````

- PUT http://localhost:8080/room/v1/reservation/{id}

````
{
	"roomNumber" : 123,
	"checkIn" : "2019-05-03",
	"checkOut" : "2019-05-09",
	"price" : 210
}
````

-DELETE http://localhost:8080/room/v1/reservation/{id}

-GET http://localhost:8080/room/v1/reservation/{id}

-GET http://localhost:8080/room/v1/reservation/

