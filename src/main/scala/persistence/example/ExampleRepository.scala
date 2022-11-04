package com.micro
package persistence.example

import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile.api._
import slick.jdbc.PostgresProfile.columnTypes
import slick.jdbc.{PositionedParameters, SetParameter}

import java.sql.Timestamp
import java.time.Instant
import java.util.UUID
import scala.concurrent.Future

class ExampleRepository(db: Database) {

  def insert(e: ExampleEntity): Future[Int] =
    db.run(ExampleTable.table += e)

  def findAll: Future[Seq[ExampleEntity]] =
    db.run(ExampleTable.table.result)

  implicit object SetUUID extends SetParameter[UUID] {
    def apply(v: UUID, pp: PositionedParameters): Unit = { pp.setBytes(columnTypes.uuidJdbcType.toBytes(v)) }
  }
  implicit object SetInstant extends SetParameter[Instant] {
    def apply(v: Instant, pp: PositionedParameters): Unit = { pp.setTimestamp(new Timestamp(v.toEpochMilli)) }
  }

  def insertIfNotExists(e: ExampleEntity): Future[Int] = {
    var qry =
      sqlu"""insert into example_table (id, name, date_time, note) 
              select ${e.id}, ${e.name}, ${e.dateTime}, ${e.note} 
                where not exists (select 1 from example_table where name = ${e.name})"""
    db.run(qry)
  }

}
