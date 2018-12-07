package controllers

import javax.inject._
import models.Field
import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._
/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  val fields = List(Field("firstName", "String", true), Field("lastName", "String", true), Field("password", "String", true), Field("ages", "Int", false))
  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def get(name: String) = Action {implicit request: Request[AnyContent] =>
    val field = fields.find(f => f.name.equals(name))
    field match {
      case Some(f) => Ok(Json.toJson(f))
      case None => BadRequest
    }
  }

  def getAll = Action {
    Ok(Json.toJson(fields))
  }

  implicit val locationWrites: Writes[Field] = (
    (JsPath \ "name").write[String] and
      (JsPath \ "kind").write[String] and
      (JsPath \ "required").write[Boolean]
    )(unlift(Field.unapply))

}
