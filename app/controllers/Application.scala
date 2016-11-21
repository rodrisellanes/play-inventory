package controllers

import javax.inject.Inject

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.mvc.{Action, Controller}
import slick.driver.JdbcProfile

class Application @Inject()(dbConfigProvider: DatabaseConfigProvider) extends Controller {

  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val dbConnection = dbConfig.db

  def index = Action {

    val outString = "Number is "

    Ok(outString + dbConnection)
  }


//  def addTaskToProject(color: String, projectId: Long) = Action.async { implicit rs =>
//    projectRepo.addTask(color, projectId)
//      .map{ _ =>  Redirect(routes.Application.projects(projectId)) }
//  }
//
////  def modifyTask(taskId: Long, color: Option[String]) = Action.async { implicit rs =>
////    taskRepo.partialUpdate(taskId, color, None, None).map(i =>
////    Ok(s"Rows affected : $i"))
////  }
//  def createProject(name: String)= Action.async { implicit rs =>
//    projectRepo.create(name)
//      .map(id => Ok(s"project $id created") )
//  }
//
//  def listProjects = Action.async { implicit rs =>
//    projectRepo.all
//      .map(projects => Ok(views.html.projects(projects)))
//  }
//
////  def projects(id: Long) = Action.async { implicit rs =>
////    for {
////      Some(project) <-  projectRepo.findById(id)
////      tasks <- taskRepo.findByProjectId(id)
////    } yield Ok(views.html.project(project, tasks))
////  }
//
//  def delete(name: String) = Action.async { implicit rs =>
//    projectRepo.delete(name).map(num => Ok(s"$num projects deleted"))
//  }

}
