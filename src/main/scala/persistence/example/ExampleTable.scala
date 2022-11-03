package com.micro
package persistence.example
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

import java.time.Instant
import java.util.UUID

object ExampleTable {

  val table = TableQuery[InnerTable]

  class InnerTable(tag: Tag) extends Table[ExampleEntity](tag, None, "example_table") {
    def id       = column[UUID]("id", O.PrimaryKey)
    def name     = column[String]("name")
    def dateTime = column[Instant]("date_time")
    def note     = column[Option[String]]("note")

    def * = (id, name, dateTime, note) <> (ExampleEntity.tupled, ExampleEntity.unapply)
  }
}
