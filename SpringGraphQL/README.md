# Spring Tips: Spring GraphQL

Spring GraphQL project sample

**Deploys onto:**
http://localhost:8080/graphiql

## Queries

### Customers
```
query customers{
 customers{
  id
  name
    }
}
```
### Customer Orders
```
query customerOrders{
  customers{
    name
    orders{
      id
    }
  }
}
```

### CustomerByName
```
query customersByName{
  customersByName(name: "Guillermo"){
    id
    name
  }
}
```
## Mutations

### Add Customer

````
mutation{
  addCustomer(name: "Pedro"){
    id
    name
  }
}
````

## Subscriptions

### Customer Events

````
subscription {
  customerEvents(customerId: 2) {
    customer {
      id
      name
    }
    event
  }
}

````

**WebSocket JavaScript Test Client at:**
http://localhost:8080/index.html


https://www.youtube.com/watch?v=kVSYVhmvNCI&ab_channel=SpringDeveloper
