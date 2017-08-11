package com.signavio.workflow.connector.example.database;

import java.sql.*;

/**
 * Connects to a database and executes SQL insert/update statements.
 *
 * Set the database.uri system property to use a database other than the default.
 */
class DatabaseConnection {

  public static final String DATABASE_URI = "jdbc:mysql://localhost/connector?user=connector&password=password";
  private Connection connection;

  private void connect() throws SQLException {
    if (connection != null) {
      return;
    }
    final String databaseUri = System.getProperty("database.uri");
    connection = DriverManager.getConnection(databaseUri == null ? DATABASE_URI : databaseUri);
  }

  void executeUpdate(final String sql) {
    try {
      connect();
    } catch (SQLException exception) {
      log(exception);
    }
    try (Statement statement = connection.createStatement()) {
      System.out.println("SQL: " + sql);
      int updatedRowCount = statement.executeUpdate(sql);
      System.out.println(String.format("Updated %d rows", updatedRowCount));
    } catch (SQLException exception) {
      log(exception);
    }
  }

  private void log(SQLException exception) {
    System.err.println("SQL error: " + exception.getMessage());
  }
}
