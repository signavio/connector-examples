package com.signavio

import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * @author Christian Wiggert
 */
class CustomerStoreTest {

  private lateinit var store: CustomerStore

  @Before
  fun setup() {
    store = CustomerStore.load("/data.json")
  }

  @Test
  fun testCreateStoreFromJsonFile() {
    val customers = store.all()

    assertEquals(3, customers.size)

    val one = customers[0]
    assertEquals("Alice Allgood", one.fullName)
    assertEquals("alice@example.org", one.email)
    assertEquals(SubscriptType.BRONZE, one.subscriptionType)
    assertEquals(10, one.discount)
    assertEquals(LocalDateTime.of(2012, 2, 14, 0, 0), one.since)
  }

  @Test
  fun testById() {
    val ben = store.byId("4d5e6f")

    assertEquals("Ben Brown", ben.fullName)
    assertEquals("ben@example.org", ben.email)
    assertEquals(SubscriptType.GOLD, ben.subscriptionType)
    assertEquals(10, ben.discount)
    assertEquals(LocalDateTime.of(2012, 2, 14, 0, 0), ben.since);
  }

  @Test
  fun testByIdWithoutMatch() {
    assertFailsWith<NoSuchElementException> {
      store.byId("unknown")
    }
  }

  @Test
  fun testOptions() {
    val options = store.options()

    assertEquals(3, options.size)

    val one = options[0]
    assertEquals("1a2b3c", one.id)
    assertEquals("Alice Allgood", one.name)
  }

  @Test
  fun testOptionsByFilter() {
    var options = store.optionsByFilter("li")
    assertEquals(2, options.size)
    assertEquals("Alice Allgood", options[0].name)
    assertEquals("Charlie Chester", options[1].name)

    options = store.optionsByFilter("ROW")
    assertEquals(1, options.size)
    assertEquals("Ben Brown", options[0].name)
  }

  @Test
  fun testOptionsWithoutMatchingFilter() {
    val options = store.optionsByFilter("404 - not found")
    assertEquals(0, options.size)
  }

  @Test
  fun testOptionById() {
    val option = store.optionById("4d5e6f")

    assertEquals("4d5e6f", option.id)
    assertEquals("Ben Brown", option.name)
  }

  @Test
  fun testOptionWithoutMatchingId() {
    assertFailsWith<NoSuchElementException> {
      store.optionById("unknown")
    }
  }
}