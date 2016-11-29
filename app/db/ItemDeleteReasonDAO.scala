package db

import model.ItemDeleteReason
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import slick.lifted.TableQuery
import scala.concurrent.Future


private[db] trait ItemDeleteReasonDAO {

  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  val dbConnection = dbConfig.db

  lazy val ItemDeleteReasons = TableQuery[ItemDeleteReasonTable]

  def insert(reason: ItemDeleteReason): Future[Long]
  def update(id: Long, reason: ItemDeleteReason): Future[Int]
  def delete(id: Long): Future[Int]
  def findById(id: Long): Future[Option[ItemDeleteReason]]

}
