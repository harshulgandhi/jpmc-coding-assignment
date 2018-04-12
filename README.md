## Introduction

This is an application that aggregates trade event data based on the input provided. It follows certain predefined rules to assess whether quantity of a particular combination of Account and Security will increase or decrease. Below are a few assumptions made while developing this application.

### Assumptions

- Input data is provided in csv format
- Output data is to be written to a file in csv format
- TradeId and version combination should be unique
- This application is built on Java Runtime Environment version 1.8


### How to run application

- Run following command in the home directory of the project (where pom.xml is)
```mvn clean compile assembly:single```
- This will create ```jar``` file of the application under target directory
- Run following command to execute jar file with input file provided as command line argument
```java -jar target/securitiesmanager-0.0.1-SNAPSHOT-jar-with-dependencies.jar src/main/resources/test.txt```
- test.txt input file contains the input provided as part of problem statement
- Output is written to a csv file named ```position_records.csv``` at current location i.e. home directory of the application

### How to run test

- Run following command in the home directory of the project (where pom.xml is)
```mvn test```

P.S. : 
- One jar of the application is already present under target directory
- It is on purpose that the built artifacts (like .class, .jar) files are commited to this repository.
