package com.signavio.workflow.connector.example.customer;

import java.util.Date;

/**
 * A basic customer record.
 */
@SuppressWarnings("unused")
public class Customer {

  private String id;
  private String fullName;
  private SubscriptionType subscriptionType;
  private Integer discount;
  private Date since;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public SubscriptionType getSubscriptionType() {
    return subscriptionType;
  }

  public void setSubscriptionType(SubscriptionType subscriptionType) {
    this.subscriptionType = subscriptionType;
  }

  public Integer getDiscount() {
    return discount;
  }

  public void setDiscount(Integer discount) {
    this.discount = discount;
  }

  public Date getSince() {
    return since;
  }

  public void setSince(Date since) {
    this.since = since;
  }
}
