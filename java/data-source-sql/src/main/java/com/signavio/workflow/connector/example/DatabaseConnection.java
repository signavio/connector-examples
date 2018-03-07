package com.signavio.workflow.connector.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Connects to a database and executes SQL insert/update statements.
 * <p>
 * Set the JDBC_DATABASE_URL environment variable to use a database other than the default.
 */
public class DatabaseConnection {

  private static final String DATABASE_URL = "jdbc:mysql://localhost/connector?user=connector&password=password&useSSL=false";
  private Connection connection;

  private void connect() throws SQLException {
    if (connection != null) {
      return;
    }
    final String databaseUrl = System.getenv().getOrDefault("JDBC_DATABASE_URL", DATABASE_URL);
    connection = DriverManager.getConnection(databaseUrl);
  }

  public PreparedStatement prepareStatement(final String sql) throws SQLException {
    connect();
    return connection.prepareStatement(sql);
  }
}
