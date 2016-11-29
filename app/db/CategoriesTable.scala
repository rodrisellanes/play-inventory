package db

import model.Category
import slick.driver.MySQLDriver.api._
import scala.concurrent.Future

/**
  * Created by rdsel on 21/10/2016.
  */
class CategoriesTable(tag: Tag)
  extends Table[Category](tag, "CATEGORIES") {

  def id = column[Long]("CATEG_ID", O.PrimaryKey, O.AutoInc)
  def category = column[String]("CATEG_NAME")

  def * = (id, category) <> (Category.tupled, Category.unapply)
}

object Categories extends CategoryDAO {

  def insert(category: Category) = {
    val insertion = Categories returning Categories.map(_.id) += category
    dbConnection.run(insertion)
  }

  def delete(id: Long) =
    dbConnection.run(Categories.filter(_.id === id).delete)

  def findById(id: Long) =
    dbConnection.run(Categories.filter(_.id === id).result.headOption)

  def selectAll =
    dbConnection.run(Categories.result)
}
