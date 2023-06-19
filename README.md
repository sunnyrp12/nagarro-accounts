# Nagarro Accouts

The Statement Viewer API is a server application that handles requests to view account statements by performing searches based on date and amount ranges. It provides a convenient way for users to retrieve their account statements with flexible filtering options. The API ensures the security and integrity of user data by hashing the account number and handles exceptions gracefully.

## Prerequisites

Before running the code and tests, make sure you have the following dependencies installed:

- Java Development Kit (JDK) 11 or later
- Maven

## Getting Started

Follow these steps to run the code and execute tests:

1. Clone the repository:

   ```bash
   https://github.com/sunnyrp12/naggaro-accounts.git

2. Change to the project directory:

    ```bash
   cd naggaro-accounts

3. Build the project using Maven:
    
   ```bash
   mvn clean install

4. Run the application:
    
   ```bash
   mvn spring-boot:run
   
5. API Endpoints are ready to use

6. First, Login by using below API with (Admin/User) credentials and you'll get your generated session token

   ```bash
   curl --location --request GET 'localhost:8080/authenticate?username=admin&password=admin'
   
7. Use Generated Token in the `Authorization` as Type `Bearer Token` with below API (Sample Token Attached)

   ```bash
   curl --location --request GET 'localhost:8080/statements?accountId=2&fromDate=01/01/2000&toDate=01/01/2021&fromAmount=100&toAmount=400' \
   --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY4NzE4OTY5OSwiaWF0IjoxNjg3MTg5Mzk5LCJ1c2VybmFtZSI6ImFkbWluIn0.qChz5TThdCZyAbq81s1s4v2kErn0RRRjkrVCcQ1mLP3lDLT88zVmiP1vzlYd0duxnyFpZfjTWcWag4I1Gb-lnw'

8. User must provide accountId and can provide additional parameters as mentioned


## Running Test Cases

1. Test Cases can be run by using following command 

   ```bash
   mvn test
   
## SonarQube Report

Generated SonarQube Report can be found following the URL

   ```bash
   https://sonarcloud.io/summary/overall?id=sunnyrp12_naggaro-accounts