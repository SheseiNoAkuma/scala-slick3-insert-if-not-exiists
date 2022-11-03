package com.micro

import persistence.Flyway

import com.micro.persistence.example.ExampleRepository
import com.typesafe.config.{Config, ConfigFactory}
import slick.jdbc.JdbcBackend.Database

trait ApplicationModule {

  lazy val conf: Config = ConfigFactory.load()

  lazy val db     = Database.forConfig("database")
  lazy val flyway = new Flyway(conf.getConfig("database"))

  lazy val repo = new ExampleRepository(db)

}
