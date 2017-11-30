package com.signavio

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.time.LocalDateTime

enum class SubscriptType(private val value: String) {
  BRONZE("bronze"),
  SILVER("silver"),
  GOLD("gold");

  override fun toString(): String = value
}

data class Customer(val id: String, val fullName: String, val email: String, val subscriptionType: SubscriptType,
                    val discount: Int, val since: LocalDateTime)

data class Option(val id: String, val name: String)

class CustomerStore(private val customers: List<Customer>) {

  companion object {
    fun mapper(): ObjectMapper = jacksonObjectMapper()
        .registerModule(JavaTimeModule())
        .enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
        .enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING)

    fun load(jsonFile: String): CustomerStore {
      val json = this.javaClass.getResourceAsStream(jsonFile).bufferedReader().use { it.readText() }
      val customers = mapper().readValue<List<Customer>>(json)
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