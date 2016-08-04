package controllers

import models.{Customer, Descriptor}
import play.api.libs.json.Json
import play.api.mvc._

/**
  * Connector endpoints controller that serves JSON responses.
  */
class Application extends Controller {

  implicit val customerWrites = Json.writes[Customer]

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
    Ok(Json.toJson(Customer.list(filter)))
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