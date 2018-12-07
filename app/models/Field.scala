package models


case class Field(name: String, kind: String, required: Boolean)

case class Model(name: String, tableName: String, fields: Seq[Field])