package com.signavio

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.jackson.*
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.jetty.*

data class ErrorResponse(val message: String)

/**
 * @author Christian Wiggert
 */
fun Application.module() {
  val store = CustomerStore.load("/data.json")
  val descriptor = this.javaClass.getResourceAsStream("/descriptor.json").bufferedReader().use { it.readText() }

  install(CallLogging)
  install(ContentNegotiation) {
    jackson {
      registerModule(JavaTimeModule())
      enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
      enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING)
      configure(SerializationFeature.INDENT_OUTPUT, true)
    }
  }
  install(StatusPages) {
    exception<NotFoundException> {
      call.respond(HttpStatusCode.NotFound, ErrorResponse(it.message))
    }
    exception<BadRequestException> {
      call.respond(HttpStatusCode.BadRequest, ErrorResponse(it.message))
    }
  }
  install(Routing) {
    get("/") {
      call.respondText(descriptor, ContentType.Application.Json)
    }
    get("/customers/options") {
      val filter = call.parameters["filter"]
      if (filter != null) {
        call.respond(store.optionsByFilter(filter))
      } else {
        call.respond(store.options())
      }
    }
    get("/customers/options/{id}") {
      val id = call.parameters["id"] ?: throw BadRequestException("No valid ID was provided.")
      val option = store.optionById(id) ?: throw NotFoundException("The customer with ID '$id' does not exist.")
      call.respond(option)
    }
    get("/customers/{id}") {
      val id = call.parameters["id"] ?: throw BadRequestException("No valid ID was provided.")
      val customer = store.byId(id) ?: throw NotFoundException("The customer with ID '$id' does not exist.")
      call.respond(customer)
    }

  }
}

fun main(args: Array<String>) {
  embeddedServer(Jetty, commandLineEnvironment(args)).start()
}