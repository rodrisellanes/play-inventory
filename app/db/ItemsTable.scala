package db

import model.{Item, ItemMappingReverse}
import slick.driver.MySQLDriver.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration


class ItemsTable(tag: Tag) extends Table[Item](tag, "ITEMS") {

  def id = column[Long]("ITEM_ID", O.PrimaryKey, O.AutoInc)
  def date = column[String]("DATE")
  def name = column[String]("ITEM")
  def price = column[Double]("PRICE")
  def category = column[Long]("CATEGORY")
  def userAssigned = column[Long]("USER_ASSIGNED")
  def active = column[Boolean]("ACTIVE", O.Default(true))

  def * = (id, date, name, price, category, userAssigned, active) <> (Item.tupled, Item.unapply)

  // ForeignKeys (categories and userAssigned)
  def categoryFK = foreignKey("CATE_FK", category, TableQuery[CategoriesTable])(
    _.id, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)

  def userAssignedFK = foreignKey("USER_FK", userAssigned, TableQuery[UsersTable])(
    _.id, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)

}

object Items extends ItemDAO {

  def insert(item: Item) = {
    val insertion = Items returning Items.map(_.id) += item
    dbConnection.run(insertion)
  }

  def update(id: Long, item: Item) =
    dbConnection.run(Items.filter(_.id === id).update(item))

  def delete(id: Long) =
    dbConnection.run(Items.filter(_.id === id).delete)

  def findById(id: Long) =
    dbConnection.run(Items.filter(_.id === id).result.headOption)

  def selectAll =
    dbConnection.run(Items.result)

  // Methods use for JOIN query's
  private lazy val formattedQuery =
    for {
      item  <- Items if item.active === true
      cate  <- Categories.Categories if cate.id  === item.category
      user <- Users.Users if user.id === item.userAssigned
    } yield (item.id, item.date, item.name, item.price, cate.category, user.fullName)

  def selectAllFormattedQuery = {
    dbConnection.run(formattedQuery.result)
  }

  def detailsList = Await.result(selectAllFormattedQuery, Duration.Inf).map(toCaseClass _)
  private[this] def toCaseClass(items: (Long, String, String, Double, String, String)) = ItemMappingReverse.tupled(items)
}
