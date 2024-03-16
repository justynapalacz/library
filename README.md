
## Prerequisites

- Java 21
- Maven (for building the project)
- MySql database
- Database client for example: MySQL Workbench

## How to Run

1. Clone the repository:

    ```bash
    git clone https://github.com/justynapalacz/library.git
    ```
2. Switch to develop branch.

    ```bash
    git checkout develop
    ```
   
3. Run database script in database client

    ```bash
    database.sql
    ```
4. Run schema script in database client

    ```bash
    schema.sql
    ```

5. Navigate to the project directory:

    ```bash
    cd library
    ```

6. Build the project:

    ```bash
    mvn clean install
    ```

7. Run application in IntelliJ
