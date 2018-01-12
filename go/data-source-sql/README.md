# Custom SQL data source

This ‘connector’ is an example of a Signavio Workflow Accelerator [custom data connector](https://docs.signavio.com/userguide/workflow/en/integration/connectors.html).
The purpose of this connector is to provide an example connector implementation in Go that demonstrates querying an SQL database.

## Features

* Returns a simple options list (ID, name) of country names and [ISO 3166-1 alpha-2](https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2) country codes (no ‘fetch one’ endpoint)
* JSON configuration file for database driver name and URL
* Separated code for HTTP server and database access
* Minimal code and dependencies

## Usage

```shell
http get http://localhost:9000/

http get http://localhost:9000/country/options

http get 'http://localhost:9000/country/options?filter=French'

http get http://localhost:9000/country/options/FR
```

## Installation

Install pre-requisites

* [Go](https://golang.org)
* MySQL database

Clone this repository.

## Configuration

1. Create a MySQL database and database user:

        create database database_name
        create user 'database_user'@'localhost' identified by 'database_password';
        grant select on database_name.* to 'database_user'@'localhost';
2. Run the `create-database.sql` script to populate a database:

        mysql -u root
        use database_name
        source create-database.sql
3. Copy the `configuration.example.json` file to the `configuration.json`.
4. In `configuration.json`, add the database name, user name and password.

## Running the server

To run the server, enter:

```shell
go run server.go
```

## Asking questions

Ask the authors by email or on Slack.

## Contributing

Contact the authors by email or on Slack to discuss your ideas.

## Authors/maintainers

Peter Hilton, with help from Stefano da Ros.

## License

This whole _connector-examples_ repository is licensed under the Apache License 2.0.
