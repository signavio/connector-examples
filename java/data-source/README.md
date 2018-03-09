# Custom data source

This data source is an example of a Signavio Workflow Accelerator [custom data connector](https://docs.signavio.com/userguide/workflow/en/integration/connectors.html).
The purpose of this connector is to demonstrate a Java implementation.

You can find both [descriptor](/java/data-source/src/main/resource/descriptor.json) for this connector and the [data](/java/data-source/src/main/resource/data.json) in this repository.

## Usage

```shell
http get http://localhost:5000/

http get http://localhost:5000/customer/options

http get 'http://localhost:5000/customer/options?filter=Alice'

http get http://localhost:5000/customer/options/1a2b3c
```

## Installation

Clone this repository and install pre-requisites:

* [Java 8](https://www.java.com/)
* [Maven 3.x](http://maven.apache.org/)

There is no configuration.

## Running the server

To run the server type:

```shell
mvn compile exec:java
```

## Asking questions

Ask the authors by email or on Slack.

## Contributing

Contact the authors by email or on Slack to discuss your ideas.

## Authors/maintainers

Christian Wiggert and Peter Hilton.

## License

This _connector-examples_ repository is licensed under the Apache License 2.0.