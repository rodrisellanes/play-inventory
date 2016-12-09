package controllers

import com.google.inject.Inject
import db.{Items, Users}
import model.User
import play.api.data.Form
import play.api.data.Forms._
import services.Services
import play.api.mvc.{Action, Controller}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.Play.current
import play.api.i18n.Messages.Implicits._


class Application @Inject extends Controller {


  val userForm = Form(
    mapping(
      "id" -> ignored(NOT_IMPLEMENTED.toLong),
      "fullName" -> nonEmptyText,
      "branch" -> longNumber
    )(User.apply)(User.unapply)
  )

  def createUser = Action { implicit request =>
    Ok(views.html.createUser(userForm))
  }

  def insertUser = Action { implicit request =>
    userForm.bindFromRequest.fold(
      formWithErrors => {
        // binding failure, you retrieve the form containing errors
        BadRequest(views.html.createUser(formWithErrors))
      },
      userData => {
        // binding success, you get the actual value
        Users.insert(userData)
        Redirect(routes.Application.selectAllUsers).flashing("success" -> "User saved!")
      }
    )

  }

  def selectAllUsers = Action {
    Ok(views.html.allUsers(Users.detailsList))

  }

  // =============== ssss ===============

  def dbSchema = Action {
    val service = Services
    service.init
    Ok("Database Created!")
  }


  def selectAllItems = Action {
    Ok(views.html.indexItems(Items.detailsList))

  }

  def show(id: Long) = Action.async {
    Items.findById(id).map { item =>
      Ok(views.html.main(item))
    }
  }


}
