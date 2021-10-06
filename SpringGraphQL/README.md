# Spring Tips: Spring GraphQL

Spring GraphQL project sample

**Deploys onto:**
http://localhost:8080/graphiql

## Test queries

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

https://www.youtube.com/watch?v=kVSYVhmvNCI&ab_channel=SpringDeveloper
