#overactive_assessment

##Back End Java Developer

###Homework Problem

All candidates who are a fit for this role will be required to do a homework problem before any
interviews. This will be the basis for the first client interview. Your solution must be coded in
Java and use Spring Boot.


A retailer offers a rewards program to its customers, awarding points based on each recorded
purchase.


A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point
for every dollar spent over $50 in each transaction
(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).


Given a record of every transaction during a three-month period, calculate the reward points
earned for each customer per month and total.

####Technical Notes and Architecture:

- Code your solution in Java and Spring
- Make up a data set to best demonstrate your solution
- Use OOP concepts as much as possible when designing classes.
- Swagger/OpenAPI (Optional) - helps communicate the contract in a better way
- Document all response codes expected. The REST API should be sending the
appropriate code and not always 200.
- Functional Programming constructs of Java 8 as appropriate.
- Generic exception handler for un-anticipated exceptions.
- Assume that the client will look at your commit history, so it should reflect a good project
progression.

####Additional Requirements:

- Follow standard best practices for structuring the code.
- Prepare and provide Test Data along with the code.
- Both positive and negative unit test cases for all operations – to be run as part of the
build process.
- Implement RestAPI’s for all CRUD operations – in this case – creating/updating
transactions, calculating and providing reward information for a User. (Java Backend/Full
Stack)
- Consistent error handling and reporting of all failures including unexpected error
conditions
- Use of appropriate logging levels, framework
- Reward calculation logic should be accurate
- Readme file is mandatory – must contain the steps required to build and run/test
the code

- The solution must be checked into GitHub (provide a public GitHub url)

How to Submit:

- Check solution into GitHub
- Send email to Overactive with the public GitHub URL
# Read me
##API Architecture Solution Brief

REST API in three layers:

    - REST Controlers
    - Business Services
    - Entities Repositories

Composed by two Domain objects

    - Reward Points
    - Transactions

###Reward Points    
Related with all client's reward points, these are not persisted but calculated every time by the rules indicated above

####Points Calculator
Functional Interface Component implemented by each reward points calculation

    components
        PointsCalculator
            RewardPoints1PointCalculator
            RewardPoints2PointsCalculator

Each PointCalculator should fulfill each point calculation rule by implement *calculate(BigDecimal)* method
Regarding calculations requested, a base amount variable is considered customizable by *rewardpoints.properties*
~~~~
_2PointsCalculator.baseAmount=100
_1PointsCalculator.baseAmount=50
~~~~

####End points
3 endpoints have been defined for each Reward Points requested operations 
~~~~
GET /api/v1/rewards/clients 
~~~~
Gets all applicable Reward Points by all clients
~~~~
GET /api/v1/rewards/clients/{clientId}
~~~~
Gets all applicable Reward Points by client 
~~~~
GET /api/v1/rewards/clients/{clientId}/{period}
~~~~
Gets all applicable Rewards Points by client and period, which can be monthly or total (as default)

###Transactions 
Related to commercial operations done by the clients, identified by: 
~~~~
Id          : Unique identifier
clientId    : Client who owns it
amount      : Total transaction amount (taxes and other sub amounts were omitted)  
date        : Transaction date
applicable  : Applicable flag, that allows logical turning off of transaction for rewards points calculation
~~~~

####End points
~~~~
GET /api/v1/transactions
~~~~
Gets all transactions persisted
~~~~
POST /api/v1/transactions
~~~~
Persist a new transaction to DB
~~~~
PUT /api/v1/transactions
~~~~
Edits a persisted transaction
~~~~
GET /api/v1/transactions/{tranId}
~~~~
Get transaction by ID
~~~~
DELETE /api/v1/transactions/{tranId}
~~~~
Removes a transaction by ID from DB

###REST API Requests & Responses

####Accepts JSON

Example:
*POST /api/v1/transactions*
~~~~
{
"id" : 100,
"clientId": "CLI010",
"amount": "3500",
"date": "2020-09-03",
"applicable": "false"
}
~~~~

####Returns JSON:

Example:
*GET /api/v1/transactions/1*
~~~~

{
"data": [
    {
    "id": 1,
    "clientId": "CLI001",
    "amount": 120,
    "date": "2020-09-01T04:00:00.000+00:00",
    "applicable": true
    }
],
"meta": {
    "request": "Sun Sep 19 20:36:26 CLST 2021",
    "api": "v1",
    "status": "200"
    }
}
~~~~

####Generic Response 
Is composed by:
- data : Refers to the data requested and is presented as an array of elements
- meta: Refers to response metadata like request timestamp, API version, HTTP status code, and error messages if applies

###Persistence Layer
It is implemented as in-memory H2 with Spring Data: 
~~~~
spring.datasource.url=jdbc:h2:mem:rewards
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
spring.jpa.properties.hibernate.format_sql=true
~~~~
Easily changeable to any DBMS supported 

###Further Improvements
- Date range filters for both domain object get endpoint
- Filter by applicable flag transactions
- Format date on responses
- Refactor metadata map boilerplate code in controllers
- Add AuthN & AuthZ capabilities
- REST testing

##Run it with Maven:
~~~~
mvn test spring-boot:run
~~~~
##Test it with Swagger UI at:
~~~~
http://localhost:8080/swagger-ui/
~~~~


Have fun! 