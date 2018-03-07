package com.signavio.workflow.connector.example.product;

import java.util.Date;

/**
 * A basic product record.
 */
public class Product {

  private String id;
  private String name;
  private ProductRange range;
  private Date added;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setRange(ProductRange range) {
    this.range = range;
  }

  public void setAdded(Date added) {
    this.added = added;
  }
}
