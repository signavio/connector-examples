package com.signavio.workflow.connector.example;

import com.signavio.workflow.connector.example.customer.Customer;
import com.signavio.workflow.connector.example.customer.CustomerOption;
import com.signavio.workflow.connector.example.customer.CustomerService;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CustomerServiceTest {


  @Test
  public void testLoadDataAndGetCustomer() throws IOException {
    CustomerService service = new CustomerService();

    service.loadData("data.json");

    Customer customer = service.find("1a2b3c");
    assertNotNull(customer);
    assertEquals("1a2b3c", customer.getId());
    assertEquals("Alice Allgood", customer.getFullName());
  }

  @Test
  public void testGetCustomerOptions() throws IOException {
    CustomerService service = new CustomerService();

    service.loadData("data.json");

    List<CustomerOption> options = service.findOptions("be");
    assertEquals(1, options.size());
    assertEquals("4d5e6f", options.get(0).getId());
    assertEquals("Ben Brown", options.get(0).getName());
  }
}
