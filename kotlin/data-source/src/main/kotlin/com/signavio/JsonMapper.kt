package com.signavio

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * @author Christian Wiggert
 */

const val DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

class ISODateSerializer : JsonSerializer<LocalDateTime>() {

  private val formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)

  override fun serialize(value: LocalDateTime?, jsonGenerator: JsonGenerator?, serializeProvider: SerializerProvider?) {
    jsonGenerator?.writeString(formatter.format(value))
  }

  override fun handledType(): Class<LocalDateTime> = LocalDateTime::class.java
}

fun jsonMapper(): ObjectMapper = jacksonObjectMapper()
    .registerModule(JavaTimeModule().addSerializer(ISODateSerializer()))
    .enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
    .enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING)
    .configure(SerializationFeature.INDENT_OUTPUT, true)
    .setDateFormat(SimpleDateFormat(DATE_TIME_PATTERN))