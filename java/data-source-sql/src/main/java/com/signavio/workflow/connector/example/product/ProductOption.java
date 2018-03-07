package com.signavio.workflow.connector.example.product;

public class ProductOption {

  // Fields are read during JSON serialisation.

  @SuppressWarnings("unused")
  private String id;
  @SuppressWarnings("unused")
  private String name;

  public ProductOption(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public ProductOption(Product product) {
    if (product == null) {
      throw new IllegalArgumentException("Missing product");
    }
    this.id = product.getId();
    this.name = product.getName();
  }
}
