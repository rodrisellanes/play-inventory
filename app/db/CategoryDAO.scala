package db

import model.Category
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import slick.lifted.TableQuery
import scala.concurrent.Future


private[db] trait CategoryDAO {

  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  val dbConnection = dbConfig.db

  lazy val Categories = TableQuery[CategoriesTable]

  def insert(category: Category): Future[Long]
  def delete(id: Long): Future[Int]
  def findById(id: Long): Future[Option[Category]]
  def selectAll: Future[Seq[Category]]

}
