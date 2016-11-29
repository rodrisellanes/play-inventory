package db

import model.ItemDeleteReason
import slick.driver.MySQLDriver.api._


class ItemDeleteReasonTable (tag: Tag) extends Table[ItemDeleteReason](tag, "DEL_REASON") {

  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
  def reason = column[String]("REASON")
  def itemId = column[Long]("ITEM_ID")

  def * = (id, reason, itemId) <> (ItemDeleteReason.tupled, ItemDeleteReason.unapply)

  def itemIdFK =  foreignKey("ITEM_FK", itemId, TableQuery[ItemsTable])(
    _.id, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
}

object ItemDeleteReasons extends ItemDeleteReasonDAO {

  def insert(reason: ItemDeleteReason) = {
    val insertion = ItemDeleteReasons returning ItemDeleteReasons.map(_.id) += reason
    dbConnection.run(insertion)
  }

  def update(id: Long, reason: ItemDeleteReason) =
    dbConnection.run(ItemDeleteReasons.filter(_.id === id).update(reason))

  def delete(id: Long) =
    dbConnection.run(ItemDeleteReasons.filter(_.id === id).delete)

  def findById(id: Long) =
    dbConnection.run(ItemDeleteReasons.filter(_.id === id).result.headOption)


}
