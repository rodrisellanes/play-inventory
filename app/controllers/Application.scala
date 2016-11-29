package controllers

import com.google.inject.Inject
import db.Items
import services.Services
import play.api.mvc.{Action, Controller}
import play.api.libs.concurrent.Execution.Implicits.defaultContext


class Application @Inject extends Controller {

  def dbSchema = Action {
    val service = Services
    service.init
    Ok("Database Created!")
  }

//  def selectAll = Action.async {
//    Items.selectAllFormattedQuery.map { item =>
//      Ok(views.html.index(item))
//    }

  def selectAll = Action {
    Ok(views.html.index(Items.detailsList))

  }

  def show(id: Long) = Action.async {
    Items.findById(id).map { item =>
      Ok(views.html.main(item))
    }
  }


}
