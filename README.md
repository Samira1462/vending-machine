# code challenge

## Technology Stack

* Java 17
* Spring boot 2.7.6
* Spring security 6
* Jpa
* Maven

## Description

Design an API for a vending machine, allowing users with a “seller” role to add, update or remove products, 
while users with a “buyer” role can deposit coins into the machine and make purchases. Your vending machine 
should only accept 5, 10, 20, 50 and 100 cent coins

## **Tasks**

- REST API should be implemented consuming and producing “application/json”
- Implement product model with amountAvailable, cost (should be in multiples of 5), productName and sellerId fields
- Implement user model with username, password, deposit and role fields
- Implement an authentication method (basic, oAuth, JWT or something else, the choice is yours)
- All the endpoints should be authenticated unless stated otherwise
- Implement CRUD for users (POST /user should not require authentication to allow new user registration)
- Implement CRUD for a product model (GET can be called by anyone, while POST, PUT and DELETE can be called only by the seller user who created the product)
- Implement /deposit endpoint so users with a “buyer” role can deposit only 5, 10, 20, 50 and 100 cent coins into their vending machine account (one coin at the time)
- Implement /buy endpoint (accepts productId, amount of products) so users with a “buyer” role can buy a product (shouldn't be able to buy multiple different products at the same time) with the money they’ve deposited. API should return total they’ve spent, the product they’ve purchased and their change if there’s any (in an array of 5, 10, 20, 50 and 100 cent coins)
- Implement /reset endpoint so users with a “buyer” role can reset their deposit back to 0
- Take time to think about possible edge cases and access issues that should be solved

## Prerequisite

### DATABASE Console
http://localhost:9000/

```shell
INSERT INTO roles(roleName) VALUES('SELLER');
INSERT INTO roles(roleName) VALUES('BUYER');
```


## Build

```shell
mvn clean package -DskipTests=true
mvn test
```

## Install

```shell
docker-compose up

mvn spring-boot:run
```

