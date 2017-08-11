package com.signavio.workflow.connector.example.database;

public class CaseStartedEvent {

  private final DatabaseConnection database;
  private final String caseId;

  CaseStartedEvent(final DatabaseConnection database, final String caseId) {
    this.database = database;
    this.caseId = caseId;
  }

  void save() {

  }
}
