package com.signavio.workflow.connector.example.database;

import spark.Request;
import spark.Response;

import java.io.IOException;

import static spark.Spark.post;

/**
 * Web service that saves workflow case started events:
 *
 * POST /58b9aaa6d1dfff24347d0e35
 */
public class Connector {

  final static DatabaseConnection database = new DatabaseConnection();

  public static void main(String[] args) {
    new CaseEventTable().create(database);
    post("/:case", Connector::saveCreateStartedEvent);
  }

  private static String saveCreateStartedEvent(final Request request, final Response response)
    throws IOException {

    System.out.println(String.format("HTTP %s %s", request.requestMethod(), request.uri()));
    final String caseId = request.params(":case");
    new CaseStartedEvent(database, caseId).save();
    response.status(201);
    return "";
  }
}
