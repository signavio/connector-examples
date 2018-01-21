package com.signavio

import com.fasterxml.jackson.module.kotlin.readValue
import java.time.LocalDateTime

enum class SubscriptionType(private val value: String) {
  BRONZE("bronze"),
  SILVER("silver"),
  GOLD("gold");

  override fun toString(): String = value
}

data class Customer(val id: String, val fullName: String, val email: String, val subscriptionType: SubscriptionType,
                    val discount: Int, val since: LocalDateTime)

data class Option(val id: String, val name: String)

class CustomerStore(private val customers: List<Customer>) {

  companion object {
    fun load(jsonFile: String): CustomerStore {
      val json = this.javaClass.getResourceAsStream(jsonFile).bufferedReader().use { it.readText() }
      val customers = jsonMapper().readValue<List<Customer>>(json)
      return CustomerStore(customers)
    }
  }

  fun all(): List<Customer> = customers

  fun byId(id: String): Customer? = customers.firstOrNull { id == it.id }

  fun options(): List<Option> = customers.map { Option(it.id, it.fullName) }

  fun optionsByFilter(filter: String): List<Option> = customers
        .filter { it.fullName.contains(filter, true) }
        .map { Option(it.id, it.fullName) }

  fun optionById(id: String): Option? {
    val customer = byId(id)

    return if (customer != null) Option(customer.id, customer.fullName) else null
  }
}