package com.micro
package persistence.example

import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

class ExampleRepository(db: Database) {

  def insert(e: ExampleEntity): Future[Int] =
    db.run(ExampleTable.table += e)

  def findAll: Future[Seq[ExampleEntity]] =
    db.run(ExampleTable.table.result)

}
