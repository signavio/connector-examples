# Custom SQL data source

This is an example of a custom data source that provides data from a relational database.
You can find this connector’s [descriptor](/java/data-source-sql/src/main/resource/descriptor.json) in this repository.

## Requirements

In order to run this example, you need the following software installed:

* [Java 8](https://www.java.com/)
* [Maven 3.x](http://maven.apache.org/)

## Run the server

To build the application and run the server type:

```shell
mvn install exec:java
```

## Deploy to production

Build the standalone JAR file and copy it to the production:

```shell
mvn install
cp target/*-jar-with-dependencies.jar ~/releases/
```

On the production server, set the database connection URL:

```shell
export JDBC_DATABASE_URL='jdbc:mysql://localhost/connector?user=connector&password=password&useSSL=false'
```

or on Windows (without quoted environment variable value):

```shell
set JDBC_DATABASE_URL=jdbc:mysql://localhost/connector?user=connector&password=password&useSSL=false
```

Start the server:

```shell
java -jar data-source-1.0-SNAPSHOT-jar-with-dependencies.jar
```
