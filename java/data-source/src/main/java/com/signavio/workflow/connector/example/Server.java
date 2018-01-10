package com.signavio.workflow.connector.example;

import com.signavio.workflow.connector.example.customer.Customer;
import com.signavio.workflow.connector.example.customer.CustomerOption;
import com.signavio.workflow.connector.example.customer.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.*;
import spark.utils.IOUtils;

import java.io.IOException;

import static spark.Spark.*;

/**
 * Starts an embedded HTTP server on {@link Server#PORT} which provides the connector interface.
 */
public class Server {

  private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

  /**
   * Defines the port the embedded HTTP server will bind to.
   */
  private static final int PORT = 5000;
  private static CustomerService customerService = new CustomerService();
  private static String descriptor;

  public static void main(String[] arguments) {
    try {
      customerService.loadData("data.json");
      descriptor = IOUtils.toString(Server.class.getClassLoader().getResourceAsStream("descriptor.json"));
    } catch (IOException exception) {
      LOGGER.error("Couldn't initialize the data.", exception);
      return;
    }

    // Server configuration
    port(PORT);
    ResponseTransformer responseTransformer = new JsonTransformer();

    // Request handlers
    get("/", (request, response) -> descriptor);
    get("/customer/options", Server::serveOptions, responseTransformer);
    get("/customer/options/:id", Server::serveOption, responseTransformer);
    get("/customer/:id", Server::serveRecord, responseTransformer);

    // Map a Java exception type to an HTTP response status.
    exception(NotFoundException.class, (error, request, response) -> {
      response.status(404);
    });

    // Define the response content type for all endpoints.
    after((request, response) -> {
      response.type("application/json;charset=UTF-8");
    });
  }

  /**
   * Serves <a href="https://docs.signavio.com/userguide/workflow/en/integration/connectors.html#record-details">Record details</a>.
   */
  private static Object serveRecord(Request request, Response response) throws NotFoundException {
      String id = request.params("id");
      LOGGER.info("Fetching customer with ID: " + id);
      Customer customer = customerService.find(id);
      if (customer == null) {
        LOGGER.info("Did not find customer.");
        throw new NotFoundException();
      }
      return customer;
  }

  /**
   * Serves a <a href="https://docs.signavio.com/userguide/workflow/en/integration/connectors.html#record-type-option-single-option">Record type option (single option)</a>.
   */
  private static Object serveOption(Request request, Response response) throws NotFoundException {
      String id = request.params("id");
      LOGGER.info("Fetching customer option with ID: " + id);
      CustomerOption option = customerService.findOneOption(id);
      if (option == null) {
        LOGGER.info("Did not find option.");
        throw new NotFoundException();
      }
      return option;
  }

  /**
   * Serves <a href="https://docs.signavio.com/userguide/workflow/en/integration/connectors.html#connector-type-options">Record type options</a>.
   */
  private static Object serveOptions(Request request, Response response) {
    String filter = request.queryParams("filter");
    return customerService.findOptions(filter);
  }

  private static class NotFoundException extends Exception {

  }
}
