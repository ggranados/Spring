# overactive_assessment

A retailer offers a rewards program to its customers, awarding points based on each recorded
purchase.
A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point
for every dollar spent over $50 in each transaction
(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).
Given a record of every transaction during a three month period, calculate the reward points
earned for each customer per month and total.
· Make up a data set to best demonstrate your solution
· Check solution into GitHub


###
GET http://localhost:8080/api/v1/rewardspoints/{{clientId}}/monthly

<> 2021-09-09T012912.200.json
http://localhost:8080/api/v1/rewardspoints/CLI001/monthly
{
"agosto": 300,
"septiembre": 90
}

###
GET http://localhost:8080/api/v1/rewardspoints/clients/all

<> 2021-09-09T013608.200.json
http://localhost:8080/api/v1/rewardspoints/clients/all
{
"CLI001": 390,
"CLI003": 310,
"CLI002": 150
}

###
GET http://localhost:8080/api/v1/rewardspoints/{{clientId}}/total

<> 2021-09-09T012945.200.json
http://localhost:8080/api/v1/rewardspoints/CLI001/total
{
"CLI001": 390
}

###
GET http://localhost:8080/api/v1/transactions/all

<> 2021-09-09T013624.200.json
http://localhost:8080/api/v1/transactions/all
[
{
"id": 1000,
"clientId": "CLI001",
"amount": 120.00,
"date": "2020-09-01T04:00:00.000+00:00"
},
{
"id": 1001,
"clientId": "CLI001",
"amount": 200.00,
"date": "2020-08-01T04:00:00.000+00:00"
},
{
"id": 1002,
"clientId": "CLI001",
"amount": 100.00,
"date": "2020-08-01T04:00:00.000+00:00"
},
{
"id": 1003,
"clientId": "CLI002",
"amount": 150.00,
"date": "2020-09-01T04:00:00.000+00:00"
},
{
"id": 1004,
"clientId": "CLI003",
"amount": 230.00,
"date": "2020-09-01T04:00:00.000+00:00"
}
]
