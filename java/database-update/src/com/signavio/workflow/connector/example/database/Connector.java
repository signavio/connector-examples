package com.signavio.workflow.connector.example.database;

public class Connector {

  public static void main(String[] args) {
    final DatabaseConnection database = new DatabaseConnection();
    initialise(database);

  }

  private static void initialise(final DatabaseConnection database) {
    final String createCaseEventTable = "create table case_event (" +
      "id integer not null primary key," +
      "case_id varchar(24) not null," +
      "created timestamp not null," +
      "type varchar(16))";
    database.executeUpdate(createCaseEventTable);
  }
}
