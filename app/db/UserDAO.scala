package db

import model.User
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import slick.lifted.TableQuery
import scala.concurrent.Future


private[db] trait UserDAO {

  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  val dbConnection = dbConfig.db

  lazy val Users = TableQuery[UsersTable]

  def insert(user: User): Future[Long]
  def update(id: Long, user: User): Future[Int]
  def delete(id: Long): Future[Int]
  def findById(id: Long): Future[Option[User]]
  def selectAll: Future[Seq[User]]

}
