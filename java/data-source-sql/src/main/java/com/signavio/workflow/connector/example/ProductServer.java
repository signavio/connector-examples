package com.signavio.workflow.connector.example;

import com.signavio.workflow.connector.example.product.*;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.http.MimeTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ResponseTransformer;
import spark.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import static spark.Spark.*;

/**
 * Starts an embedded jetty server on {@link com.signavio.workflow.connector.example.ProductServer#PORT} which provides the connector interface.
 */
public class ProductServer {

  private static final Logger LOGGER = LoggerFactory.getLogger(com.signavio.workflow.connector.example.ProductServer.class);
  final static DatabaseConnection database = new DatabaseConnection();

  /**
   * Defines the port the embedded jetty server will bind to.
   */
  private static int PORT = 5000;

  public static void main( String[] args ) {
    ProductService productService = new ProductService(database);
    try {
      LOGGER.info(String.format("Database with %d options available", productService.count()));
    } catch (SQLException e) {
      LOGGER.error("Database connection failed", e);
      return;
    }

    final String descriptor;
    try {
      InputStream resource = ProductServer.class.getClassLoader().getResourceAsStream("descriptor.json");
      descriptor = IOUtils.toString(resource);
    } catch (IOException e) {
      LOGGER.error("Couldn't initialize the data.", e);
      return;
    }

    // general server configuration
    port(PORT);

    ResponseTransformer responseTransformer = new JsonTransformer();
    // request handlers
    get("/", (req, res) -> descriptor );

    get("/product/options", (req, res) -> {
      String filter = req.queryParams("filter");
      return productService.getProductOptions(filter);
    }, responseTransformer);

    get("/product/options/:id", (req, res) -> {
      String id = req.params("id");
      LOGGER.info("Fetching product option with ID: " + id);
      ProductOption option = productService.getProductOption(id);
      if (option == null) {
        LOGGER.info("Did not find option.");
        throw new NotFoundException();
      }
      return option;
    }, responseTransformer);

    get("/product/:id", (req, res) -> {
      String id = req.params("id");
      LOGGER.info("Fetching product with ID: " + id);
      Product product = productService.getProduct(id);
Http
      if (product == null) {
        LOGGER.info("Did not find product.");
        throw new NotFoundException();
      }
      return product;
    }, responseTransformer);

    exception(NotFoundException.class, (error, req, res) -> {
      res.status(HttpStatus.NOT_FOUND_404);
    });

    exception(SQLException.class, (error, request, response) -> {
      response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
      response.body(error.getClass().getSimpleName() + ": " + error.getMessage());
    });

    // define the content type for every response
    after((req, res) -> {
      res.type(MimeTypes.Type.APPLICATION_JSON_UTF_8.asString());
    });

  }

  public static class NotFoundException extends Exception {

  }
}
