A web service that inserts workflow case events into a database.


## Pre-requisites

* MySQL
* JDK 8
* Apache Maven

## Installation (MySQL)

Create the database user and database.

```
$ mysql -uroot
mysql> create user 'connector'@'localhost' identified by 'password';
mysql> create database connector;
mysql> grant all privileges on connector.* to 'connector'@'localhost';
mysql> use connector
mysql> source src/main/sql/create-table.mysql.sql
```

Set an environment variable to the database connection URI.

    export JDBC_DATABASE_URL="jdbc:mysql://localhost/connector?user=connector&password=password"

## Installation (PostgreSQL)

Create the database user, database, and table.

```
$ psql template1
template1=# create user connector with password 'password';
template1=# create database connector owner connector;
template1=# grant all privileges on database connector to connector;
template1-# \c connector connector
connector-# \i src/main/sql/create-table.postgresql.sql
```

Set an environment variable to the database connection URI.

    export JDBC_DATABASE_URL="jdbc:postgresql://localhost/connector?user=connector&password=password"

To use a database other than MySQL or PostgreSQL, add its JDBC driver as a Maven dependency in `pom.xml`, and change the URL environment variable accordingly.

## Installation (Heroku/PostgreSQL)

To install on Heroku, using a default PostgreSQL database:

1. Copy this `database-update` directory somewhere else that isn’t in a git repository, to avoid trying to deploy this whole `connector-examples` repository.
1. Deploy the application to Heroku
   ```
   git init
   git add .
   git commit -m init
   heroku create
   git push heroku master
   ```
1. Create the table in the Heroku database
   ```
   heroku pg:psql < src/main/sql/create-table.postgresql.sql
   ```

## Usage

Run the application:

    java -jar target/connector-examples-database-update-1.0-SNAPSHOT.jar

Send a case event in an HTTP POST request, e.g. using [HTTPie](https://httpie.org/) on the command line:

    http post http://localhost:4567/58b9aaa6d1dfff24347d0e35

## Workflow Accelerator integration

To publish case events from [Signavio Workflow Accelerator](https://www.signavio.com/products/workflow-accelerator/), use the following JavaScript action.

```javascript
const handleHttpResponse = (error, response, body) => {
  console.log(`HTTP ${response.statusCode} ${response.statusMessage}`)
  if (response.statusCode != 201) {
    console.log('Unexpected response status; expected 201 CREATED')
  }
  if (error) {
    console.log(error)
  }
}

request.post(`https://database-update.herokuapp.com/${_case.id}`, handleHttpResponse)
```

Note that this example assumes that you have deployed the connector as a Heroku application called `database-update`.