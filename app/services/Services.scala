package services

import db._
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._

import scala.concurrent.Await
import scala.concurrent.duration._

object Services {

  // Connection to the database (play & slick)
  println("Connection to Database...")
  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  val dbConnection = dbConfig.db
  println("Database connected successfully: " + dbConnection)

  // Database tables should go here
  private val categoryTable = Categories.Categories
  private val branchesTable = Branches.Branches
  private val usersTable = Users.Users
  private val itemsTable = Items.Items
  private val reasonTable = ItemDeleteReasons.ItemDeleteReasons

  private val schemas = itemsTable.schema ++ usersTable.schema ++ categoryTable.schema  ++
    branchesTable.schema ++ reasonTable.schema

  private def createAllTables =
    dbConnection.run(DBIO.seq(schemas.create))

  private def dropAllTables =
    dbConnection.run(DBIO.seq(schemas.drop))

  def clearAllTables: Unit = {
    val tables = List(reasonTable, itemsTable, categoryTable, usersTable, branchesTable)
    for (table <- tables) {
      val deleteAllTablesQuery = DBIO.seq(table.delete)
      val resultFut = dbConnection.run(deleteAllTablesQuery)
      Await.result(resultFut, 2 seconds)
    }
  }

  def init = {

//    clearAllTables

    println("Drop all schema form the DB")
//    dropAllTables

    println("Creating Tables in case doesn't exist")
    createAllTables

  }


}
