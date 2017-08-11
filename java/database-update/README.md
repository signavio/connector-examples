An application that inserts workflow case events into a database.


## Pre-requisites

Install MySQL, or another JDBC-compatible database.

## Installation

Create the database user and database.
In MySQL:

```sql
create user 'connector'@'localhost' identified by 'password';
create database connector;
grant all privileges on connector.* to 'connector'@'localhost';
```

To use a database other than MySQL, add its JDBC driver as a Maven dependency in `pom.xml`.

## Usage

Use Maven to run the application.

    mvn compile exec:java
