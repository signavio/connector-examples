package com.signavio.workflow.connector.example.database;

/**
 * Database table for workflow case events.
 */
class CaseEventTable {

  final String createStatement = "create table case_event (" +
    "id integer not null primary key auto_increment," +
    "case_id varchar(24) not null," +
    "created timestamp not null," +
    "type varchar(16))";

  void create(final DatabaseConnection database) {
    database.executeUpdate(createStatement);
  }
}
