A web service that inserts workflow case events into a database.


## Pre-requisites

* MySQL
* JDK 8
* Apache Maven

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

Use Maven to run the application locally, passing the database connection URI as a system property.

    mvn compile exec:java -Ddatabase.uri="jdbc:mysql://localhost/connector?user=connector&password=password"

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

request.post(`http://localhost:4567/${_case.id}`, handleHttpResponse)
```

Note that this example assumes running both Workflow Accelerator and the database-update connector locally.
Using this connector
