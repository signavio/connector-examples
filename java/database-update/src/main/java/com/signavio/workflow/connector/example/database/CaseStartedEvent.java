package com.signavio.workflow.connector.example.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * An event that records a workflow case having started.
 */
class CaseStartedEvent {

  private final DatabaseConnection database;
  private final String caseId;

  CaseStartedEvent(final DatabaseConnection database, final String caseId) {
    this.database = database;
    this.caseId = caseId;
  }

  void save() throws SQLException {
    final String insertStatement = "insert into case_event(case_id, type, created) values(?, ?, ?)";
    PreparedStatement preparedStatement = database.prepareStatement(insertStatement);
    preparedStatement.setString(1, caseId);
    preparedStatement.setString(2, "started");
    preparedStatement.setTimestamp(3, new Timestamp(new Date().getTime()));
    preparedStatement.executeUpdate();
  }
}
