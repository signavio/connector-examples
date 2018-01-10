package com.signavio.workflow.connector.example.customer;

/**
 * Java bean that serialises to the correct JSON for a connector option.
 */
public class CustomerOption {

  private String id;
  private String name;

  public CustomerOption() {}

  public CustomerOption(Customer customer) {
    this.id = customer.getId();
    this.name = customer.getFullName();
  }

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
}
