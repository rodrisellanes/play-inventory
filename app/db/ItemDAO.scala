package db

import model.Item
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import slick.lifted.TableQuery
import scala.concurrent.Future


private[db] trait ItemDAO {

  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  val dbConnection = dbConfig.db

  lazy val Items = TableQuery[ItemsTable]

  def insert(item: Item): Future[Long]
  def update(id: Long, item: Item): Future[Int]
  def delete(id: Long): Future[Int]
  def findById(id: Long): Future[Option[Item]]
  def selectAll: Future[Seq[Item]]

}
