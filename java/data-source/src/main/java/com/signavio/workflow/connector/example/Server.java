package com.signavio.workflow.connector.example;

import com.signavio.workflow.connector.example.customer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ResponseTransformer;
import spark.utils.IOUtils;

import java.io.IOException;

import static spark.Spark.*;

/**
 * Starts an embedded jetty server on {@link Server#PORT} which provides the connector interface.
 */
public class Server {

  private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

  /**
   * Defines the port the embedded HTTP server will bind to.
   */
  private static int PORT = 5000;

  public static void main( String[] args ) {
    CustomerService customerService = new CustomerService();
    final String descriptor;
    try {
      customerService.loadData("data.json");
      descriptor = IOUtils.toString(Server.class.getClassLoader().getResourceAsStream("descriptor.json"));
    } catch (IOException e) {
      LOGGER.error("Couldn't initialize the data.", e);
      return;
    }

    // Server configuration

    port(PORT);
    ResponseTransformer responseTransformer = new JsonTransformer();

    // Request handlers

    get("/", (req, res) -> descriptor );

    get("/customer/options", (req, res) -> {
      String filter = req.queryParams("filter");
      return customerService.getCustomerOptions(filter);
    }, responseTransformer);

    get("/customer/options/:id", (req, res) -> {
      String id = req.params("id");
      LOGGER.info("Fetching customer option with id: " + id);
      CustomerOption option = customerService.getCustomerOption(id);
      if (option == null) {
        LOGGER.info("Did not find option.");
        throw new NotFoundException();
      }
      return option;
    }, responseTransformer);

    get("/customer/:id", (req, res) -> {
      String id = req.params("id");
      LOGGER.info("Fetching customer with id: " + id);
      Customer customer = customerService.getCustomer(id);
      if (customer == null) {
        LOGGER.info("Did not find customer.");
        throw new NotFoundException();
      }
      return customer;
    }, responseTransformer);

    // Map a Java exception type to an HTTP response status.
    exception(NotFoundException.class, (error, req, res) -> {
      res.status(404);
    });

    // Define the response content type for all endpoints.
    after((req, res) -> {
      res.type("application/json;charset=UTF-8");
    });

  }

  public static class NotFoundException extends Exception {

  }
}
