package models

import play.api.libs.json._

/**
 * The connector type descriptor.
 */
object Descriptor {
  val json: JsValue = JsObject(Seq(
    "key" -> JsString("customers"),
    "name" -> JsString("Customers"),
    "description" -> JsString("A database with all customers."),
    "typeDescriptors" -> JsArray(Seq(
      JsObject(Seq(
        "key" -> JsString("customer"),
        "name" -> JsString("Customer"),
        "fields" -> JsArray(Seq(
          JsObject(Seq(
            "key" -> JsString("fullName"),
            "name" -> JsString("Name"),
            "type" -> JsObject(Seq("name" -> JsString("text"))))),
          JsObject(Seq(
            "key" -> JsString("email"),
            "name" -> JsString("Email"),
            "type" -> JsObject(Seq("name" -> JsString("emailAddress"))))),
          JsObject(Seq(
            "key" -> JsString("subscriptionType"),
            "name" -> JsString("Type of the subscription"),
            "type" -> JsObject(Seq(
              "name" -> JsString("choice"),
              "options" -> JsArray(Seq(
                JsObject(Seq("id" -> JsString("bronze"), "name" -> JsString("Bronze"))),
                JsObject(Seq("id" -> JsString("silver"), "name" -> JsString("Silver"))),
                JsObject(Seq("id" -> JsString("gold"), "name" -> JsString("Gold"))))))))),
          JsObject(Seq("key" -> JsString("discount"), "name" -> JsString("Discount"), "type" -> JsObject(Seq("name" -> JsString("number"))))),
          JsObject(Seq(
            "key" -> JsString("since"),
            "name" -> JsString("Registration date"),
            "type" -> JsObject(Seq("name" -> JsString("date"), "kind" -> JsString("datetime"))))))),
        "optionsAvailable" -> JsBoolean(true),
        "fetchOneAvailable" -> JsBoolean(true))))),
    "version" -> JsNumber(1),
    "protocolVersion" -> JsNumber(1)))
}
