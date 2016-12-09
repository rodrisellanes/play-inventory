package db

import model.{User, UserMappingReverse}
import slick.driver.MySQLDriver.api._

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration


class UsersTable(tag: Tag) extends Table[User](tag, "USERS") {

  def id = column[Long]("USER_ID", O.PrimaryKey, O.AutoInc)
  def fullName = column[String]("FULL_NAME")
  def branch = column[Long]("BRANCH")

  def * = (id, fullName, branch) <> (User.tupled, User.unapply)

  // ForeignKey
  def branchFK = foreignKey("BRANCH_FK", branch, TableQuery[BranchesTable])(
    _.id, onUpdate = ForeignKeyAction.Cascade, onDelete = ForeignKeyAction.Cascade)
}

object Users extends UserDAO {

  def insert(user: User) = {
    val insertion = Users returning Users.map(_.id) += user
    dbConnection.run(insertion)
  }

  def update(id: Long, user: User) =
    dbConnection.run(Users.filter(_.id === id).update(user))

  def delete(id: Long) =
    dbConnection.run(Users.filter(_.id === id).delete)

  def findById(id: Long): Future[Option[User]] =
    dbConnection.run(Users.filter(_.id === id).result.headOption)

  def selectAll =
    dbConnection.run(Users.result)

  // Methods use for JOIN query's
  private lazy val formattedQuery =
  for {
    user  <- Users
    branch  <- Branches.Branches if user.branch  === branch.id
  } yield (user.id, user.fullName, branch.branchName)

  def selectAllFormattedQuery = {
    dbConnection.run(formattedQuery.result)
  }

  def detailsList = Await.result(selectAllFormattedQuery, Duration.Inf).map(toCaseClass _)
  private[this] def toCaseClass(users: (Long, String, String)) = UserMappingReverse.tupled(users)

}
