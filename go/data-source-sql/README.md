# Custom SQL data source

This connector is an example of a custom data source that queries an SQL database.

## Requirements

To run this example, you must have [Go](https://golang.org) installed.

Run the `create-database.sql` script to populate a database.

The code currently hard-codes the database name, user name and password all as `cldr`, and the database as MySQL.

## Run the server

To run the server, enter:

```shell
go run server.go
```
