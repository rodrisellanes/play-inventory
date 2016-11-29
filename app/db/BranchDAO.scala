package db

import model.Branch
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import slick.lifted.TableQuery
import scala.concurrent.Future


private[db] trait BranchDAO {

  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  val dbConnection = dbConfig.db

  lazy val Branches = TableQuery[BranchesTable]

  def insert(branch: Branch): Future[Long]
  def delete(id: Long): Future[Int]
  def findById(id: Long): Future[Option[Branch]]
  def selectAll: Future[Seq[Branch]]


}
