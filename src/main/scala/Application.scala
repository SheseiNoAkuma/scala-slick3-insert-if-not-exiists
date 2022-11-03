package com.micro

import persistence.example.ExampleEntity

import com.typesafe.scalalogging.LazyLogging

import java.time.Instant
import java.util.UUID
import java.util.concurrent.Executors
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor}


object Application extends App with ApplicationModule with LazyLogging {
  implicit val ex: ExecutionContextExecutor = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(10))

  flyway.migration()

  val r = for {
    _ <- repo.insert(ExampleEntity(UUID.randomUUID(), "hello", Instant.now, None))
    r <- repo.findAll
  } yield r

  val x = Await.result(r, Duration.Inf)
  logger.info(s"list: $x")
  logger.info("let's die :)")
}
