package controllers

import com.google.inject.Inject
import db.{Categories, Branches}
import model.{Item, User}
import play.api.data.Forms._
import play.api.data._
import play.api.data.format.Formats._
import play.api.mvc.{Action, Controller}
import services.Services
import play.api.i18n.Messages.Implicits._

class ApplicationGeneral @Inject() extends Controller {


  def home = Action { implicit request =>
    Ok(views.html.main("IT Inventory"))
  }

  def dbSchema = Action {
    val service = Services
    service.init
    Ok("Database Created!")
  }



}
