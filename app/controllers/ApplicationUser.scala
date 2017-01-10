package controllers

import com.google.inject.Inject
import db.Users
import model.User
import play.api.data._
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import play.api.data.format.Formats._
import play.api.Play.current
import play.api.i18n.Messages.Implicits._

class ApplicationUser @Inject() extends Controller {

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
        Redirect(routes.ApplicationUser.selectAllUsers).flashing("success" -> "User saved!")
      }
    )

  }

  def selectAllUsers = Action {
    Ok(views.html.allUsers(Users.detailsList))
  }



}
