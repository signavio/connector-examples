package com.signavio.workflow.connector.example;

import com.google.gson.*;
import com.google.gson.internal.bind.util.ISO8601Utils;
import spark.ResponseTransformer;

import java.lang.reflect.Type;
import java.sql.Date;

/**
 * Transforms our Pojos to JSON strings.
 */
public class JsonTransformer implements ResponseTransformer {

  Gson gson;

  public JsonTransformer() {
    gson = new GsonBuilder()
      .registerTypeAdapter(Date.class, new DateTimeSerializer())
      .create();
  }

  @Override
  public String render(Object object) throws Exception {
    return gson.toJson(object);
  }

  /**
   * This SQL date adapter sets the timezone to UTC and transforms date objects to
   * the required ISO 8601 format.
   * <code>
   *   2012-02-14T19:32:00.000Z
   * </code>
   */
  private class DateTimeSerializer implements JsonSerializer<Date> {
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(ISO8601Utils.format(src, true));
    }
  }

}
