# Running the application

## Requirements
1. JDK 21
2. Maven

## To run the application:

1. Git clone the repository.
2. Open the repository with your favourite IDE
3. Run `mvn clean install` in your terminal to build the application and run tests
4. Run `DemoApplication` to run the application

## DB connection

While the application is running, access: http://localhost:8080/h2-console/login.jsp 
or use your favourite DB tool. Use the following credentials to connect to the H2 DB:

1. `Driver class: org.h2.Driver`
2. `JDBC URL: jdbc:h2:mem`
3. `Username: sa`
4. `Password: password`

## Postman Collection

Import the `Odds-based Game.postman_collection.json` 
file from the root directory of this repository into postman.
This will import the postman collection, containing calls to the APIs.

