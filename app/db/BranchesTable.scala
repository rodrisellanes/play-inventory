package db

import model.Branch
import slick.driver.MySQLDriver.api._

import scala.concurrent.Future

/**
  * Created by rdsel on 21/10/2016.
  */
class BranchesTable(tag: Tag) extends Table[Branch](tag, "BRANCHES") {

  def id = column[Long]("BRANCH_ID", O.PrimaryKey, O.AutoInc)
  def branchName = column[String]("BRANCH_NAME")

  def * = (id, branchName)  <> (Branch.tupled, Branch.unapply)
}

object Branches extends BranchDAO {

  def insert(branch: Branch) = {
    val insertion = Branches returning Branches.map(_.id) += branch
    dbConnection.run(insertion)
  }

  def delete(id: Long) =
    dbConnection.run(Branches.filter(_.id === id).delete)

  def findById(id: Long) =
    dbConnection.run(Branches.filter(_.id === id).result.headOption)

  def selectAll =
    dbConnection.run(Branches.result)



}
