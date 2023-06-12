# UK-Postal

## Prerequisites

- JDK 17
- Maven
- Docker 

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Running the PostgreSQL Database

1. Start the PostgreSQL container using `docker-compose`:

   ```shell
   docker-compose up -d
   ```

2. Build and run the project using Maven:

    ``` shell
    ./mvnw spring-boot:run
    ```


3. Verify that the application is running by accessing the defined endpoints in your browser or using tools like cURL or Postman.

    For example:
    ```shell
    curl -u user:password http://localhost:8080/distance\?postcode1\=AB10%201XG\&postcode2\=AB21%209DA 
    ```
