package com.signavio.workflow.connector.example.product;

import com.signavio.workflow.connector.example.DatabaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements database queries for accessing product data.
 */
public class ProductService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
  private DatabaseConnection database;

  public ProductService(DatabaseConnection database) {
    this.database = database;
  }

  public Product getProduct(String id) throws SQLException {
    if (id != null) {
      final String query = "select * from products where id=?";
      PreparedStatement preparedStatement = database.prepareStatement(query);
      preparedStatement.setString(1, id);
      ResultSet results = preparedStatement.executeQuery();
      if (results.next()) {
        Product product = new Product();
        product.setId(results.getString("id"));
        product.setName(results.getString("name"));
        product.setRange(ProductRange.valueOf(results.getString("productRange")));
        product.setAdded(results.getDate("added"));
        return product;
      }
    }
    return null;
  }

  public List<ProductOption> getProductOptions(String filter) throws SQLException {
    boolean filterDefined = filter != null && !filter.isEmpty();
    final String query = filterDefined ?
      "select * from products where lower(name) like ? order by lower(name)" :
      "select * from products order by lower(name)";
    PreparedStatement preparedStatement = database.prepareStatement(query);
    if (filterDefined) {
      preparedStatement.setString(1, '%' + filter.trim().toLowerCase() + '%');
    }

    ResultSet results = preparedStatement.executeQuery();
    List<ProductOption> options = new ArrayList<>();
    while (results.next()) {
      options.add(new ProductOption(results.getString("id"), results.getString("name")));
    }
    return options;
  }

  public ProductOption getProductOption(String id) throws SQLException {
    return id == null ? null : new ProductOption(getProduct(id));
  }
}
