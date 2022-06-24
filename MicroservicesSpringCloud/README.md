# SpringBoot Microservice

Users - Department services SpringCloud example composed by (so far):


-User Service

	**POST:** localhost:9001/departments/

	```
	{
    "name":"IT",
    "address":"Rio de Janeiro 74",
    "code":"IT-006"
	}
	```
	
	**GET:**localhost:9001/departments/{idDepartment}

-Department Service

	**POST:** localhost:9002/users/

	```
	{
    "firstName":"Guillermo",
    "lastName":"Ganados",
    "email":"gg@gmail.com",
    "departmentId":2
	}
	```
	
	**GET:**localhost:9002/users/{idUser}

-Service Registry with Eureka
	
	http://localhost:8761/

