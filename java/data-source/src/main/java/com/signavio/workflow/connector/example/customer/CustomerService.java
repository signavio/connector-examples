package com.signavio.workflow.connector.example.customer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A simple store for static customer records.
 */
public class CustomerService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

  private Map<String, Customer> customers;

  /**
   * Loads static data from the data.json file.
   */
  public void loadData(String resource) throws IOException {
    customers = new HashMap<>();
    Type listType = new TypeToken<ArrayList<Customer>>(){}.getType();
    List<Customer> loadedCustomers = new Gson().fromJson(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(resource)), listType);
    loadedCustomers.forEach(customer -> this.customers.put(customer.getId(), customer));
    LOGGER.info(String.format("Loaded %s customer records.", customers.size()));
  }

  /**
   * Returns a complete customer record.
   */
  public Customer getCustomer(String id) {
    if (id != null) {
      return customers.get(id);
    }
    return null;
  }

  /**
   * Returns a list of customer options, matching the filter (if specified).
   */
  public List<CustomerOption> getCustomerOptions(String filter) {
    if (filter != null && !filter.isEmpty()) {
      return customers.values().stream()
        .filter(customer -> customer.getFullName().toLowerCase().contains(filter.toLowerCase()))
        .map(CustomerOption::new)
        .collect(Collectors.toList());
    }
    return customers.values().stream()
      .map(CustomerOption::new)
      .collect(Collectors.toList());
  }

  /**
   * Returns a single customer option.
   */
  public CustomerOption getCustomerOption(String id) {
    if (id != null) {
      Customer customer = customers.get(id);
      return customer != null ? new CustomerOption(customer) : null;
    }
    return null;
  }
}
