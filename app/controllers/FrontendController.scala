package controllers

import play.api._
import play.api.mvc._

class FrontendController extends Controller {

  def index = Action {
    Ok(views.html.index())
  }

  def jsRoutes(v: String = "jsRoutes") = Action { implicit request =>
    Ok(
      routing.JavaScriptReverseRouter(v)()
    ).as(JAVASCRIPT)
  }
}