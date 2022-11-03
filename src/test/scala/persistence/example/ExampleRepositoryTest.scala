package com.micro
package persistence.example

import persistence.Flyway

import com.typesafe.config.Config
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import org.scalatest.time.{Millis, Span}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import pureconfig.ConfigSource
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile.api._

import java.time.Instant
import java.util.UUID
import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

class ExampleRepositoryTest extends AnyWordSpec with Matchers with ScalaFutures with BeforeAndAfterAll with BeforeAndAfterEach {

  implicit val patience: PatienceConfig = PatienceConfig(scaled(Span(10000, Millis)), scaled(Span(1500, Millis)))
  implicit val ex: ExecutionContextExecutor = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(10))

  lazy val db = Database.forConfig("database")

  private val sut = new ExampleRepository(db)

  override def beforeAll(): Unit = {
    val conf: Config = ConfigSource.default.loadOrThrow[Config]
    val flyway       = new Flyway(conf.getConfig("database"))
    flyway.migration()
  }

  override def beforeEach(): Unit = {
    // reset table
    db.run(ExampleTable.table.delete)
  }

  "insert" should {

    "insert record if not exists" in {
      val actual   = sut.insert(ExampleEntity(UUID.randomUUID(), "whatever", Instant.now, None))
      whenReady(actual)(r => r shouldBe 1)
    }

    "fail if key already exists" in {
      val expected = ExampleEntity(UUID.randomUUID(), "whatever", Instant.now, None)
      val actual = for {
        _ <- db.run(ExampleTable.table += expected)
        a <- sut.insert(expected)
      } yield a

        whenReady(actual.failed)(r => r shouldBe a [JdbcSQLIntegrityConstraintViolationException])
    }
  }

}
