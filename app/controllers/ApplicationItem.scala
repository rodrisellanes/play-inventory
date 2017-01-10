package controllers

import com.google.inject.Inject
import db.Items
import model.Item
import play.api.data._
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import play.api.data.format.Formats._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.Play.current
import play.api.i18n.Messages.Implicits._


class ApplicationItem @Inject() extends Controller {

  val itemForm = Form(
    mapping(
      "id" -> ignored(NOT_IMPLEMENTED.toLong),
      "date" -> nonEmptyText,
      "name" -> nonEmptyText,
      "price" -> of[Double],
      "category" -> longNumber,
      "userAssigned" -> longNumber,
      "active" -> boolean
    )(Item.apply)(Item.unapply)
  )

  def createItem = Action { implicit request =>
    Ok(views.html.createItem(itemForm))
  }

  def insertItem = Action { implicit request =>
    itemForm.bindFromRequest.fold(
      formWithErrors => {
        // binding failure, you retrieve the form containing errors
        BadRequest(views.html.createItem(formWithErrors))
      },
      itemData => {
        // binding success, you get the actual value
        Items.insert(itemData)
        Redirect(routes.ApplicationItem.selectAllItems)
      }
    )
  }

  def selectAllItems = Action {
    Ok(views.html.allItems(Items.detailsList))

  }

  def deleteHistory = Action {
    Ok(views.html.historyItems(Items.detailsList))
  }



}
