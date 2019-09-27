[![Run Status](https://api.shippable.com/projects/5d86d4879e1c640007279d2d/badge?branch=master)](https://luzhangtina.github.io/)


# TinaAusBuy

Developing an order management system using Spring Boot framework and Mysql database.

## Functionality

### Product Management

* Adding product to system
* Editing product to system
* Deleting product from system
* Getting product from system

### Client Management

* Adding client to system
* Editing client to system
* Deleting client from system
* Getting client from system

### Shipping Address Management
* Adding client's shipping addresses to system
* Editing client's shipping addresses to system
* Deleting client's shipping addresses from system
* Getting client's shipping addresses from system

### Order Management
* Creating a client's order
* Editing a client's order
  ```
  1. Adding products to an order
  2. Editing products quantity of an order
  3. Deleting products from an order
  4. Editing an order's status
  5. Editing products' status in an order
  ```
* Deleting a client's order from system
* Getting a client's orders from system

### Order Report Management
* Creating order confirmation report once the order is confirmed
* Creating order billing report once the payment is done
* Deleting order reports

### Sales Performance Management
* Getting sales performance based on specified period
* Creating sales performance report based on specified period
* Getting sales performance based on client
* Creating sales performance report based on client

## Tools and Technologies

* **Technology** : Java, Spring Boot, Hibernate, Junit, Maven, Jacoco.
* **Application Server**: Apache Tomcat Server
* **Database** : Mysql Database.

## CI/CD
* Using Jacoco generate code coverage report
* Using Maven Surefire generate test report
* Integrating compilation, testing, packaging, test and code coverage report on Shippable