package models

import java.time.{LocalDate, ZoneOffset, ZonedDateTime}

case class Customer(
  id: String,
  fullName: String,
  email: String,
  subscriptionType: String,
  discount: Int,
  since: ZonedDateTime)

object Customer {

  val date = LocalDate.of(2012, 2, 14).atStartOfDay(ZoneOffset.UTC)

  // Test data.
  val data = Seq(
    Customer("1a2b3c", "Alice Allgood", "alice@example.org", "bronze", 10, date),
    Customer("4d5e6f", "Ben Brown", "ben@example.org", "gold", 10, date),
    Customer("7g8h9i", "Charlie Chester", "charlie@example.org", "silver", 15, date))

  /**
    * Returns a list of customers, optionally filtered by a case-insensitive substring match on full name.
    */
  def list(filter: Option[String]): Seq[Customer] = {
    filter.map { filterText =>
      data.filter(customer => customer.fullName.toLowerCase.contains(filterText.toLowerCase))
    }.getOrElse(data)
  }

  /**
    * Returns the customer with the given ID.
    */
  def find(id: String): Option[Customer] = data.find(_.id == id)
}
