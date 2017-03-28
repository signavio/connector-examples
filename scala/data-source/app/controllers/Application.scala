package controllers

import models.{Customer, Descriptor}
import play.api.libs.json._
import play.api.libs.json.Json
import play.api.mvc._

/**
  * Connector endpoints controller that serves JSON responses.
  */
class Application extends Controller {

  implicit val customerWrites = Json.writes[Customer]

  // Custom JSON format for a single option.
  val customerOptionWrites = new Writes[Customer] {
    def writes(customer: Customer) = Json.obj(
      "id" -> customer.id,
      "name" -> customer.fullName
    )
  }

  // Custom JSON format for a list of options.
  val customerOptionsWrites = new Writes[Seq[Customer]] {
    def writes(customers: Seq[Customer]) = JsArray(customers.map(customerOptionWrites.writes(_)).toSeq)
  }

  /**
    * Serves the connector descriptor.
    */
  def descriptor = Action {
    Ok(Descriptor.json)
  }

  /**
    * Serves the customer list options.
    */
  def customerOptions(filter: Option[String]) = Action {
    Ok(Json.toJson(Customer.list(filter))(customerOptionsWrites))
  }

  /**
    * Serves a single customer option.
    */
  def customerOption(id: String) = Action {
    Customer.find(id).map { customer =>
      Ok(Json.toJson(customer)(customerOptionWrites))
    }.getOrElse(NotFound)
  }

  /**
    * Serves a single customer.
    */
  def customer(id: String) = Action {
    Customer.find(id).map { customer =>
      Ok(Json.toJson(customer))
    }.getOrElse(NotFound)
  }
}