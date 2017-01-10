package model

// This model represent the database projection
case class Item(id: Long, date: String, name: String, price: Double, category: Long,
                userAssigned: Long, active: Boolean)
case class User(id: Long, fullName: String, branch: Long)
case class Branch(id: Long, branch: String)
case class Category(id: Long, category: String)
case class ItemDeleteReason(id: Long, reason: String, item: Long)

// This data model is useful to manage teh JOIN query's
case class ItemMappingReverse(id: Long, date: String, name: String, price: Double, category: String,
                              userAssigned: String, active: Boolean)
case class UserMappingReverse(id: Long, fullName: String, branch: String)
case class IDeleteReasonMappingReverse(id: Long, reason: String, itemAllQuery: ItemMappingReverse)