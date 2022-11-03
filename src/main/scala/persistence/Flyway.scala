package com.micro
package persistence

import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging

class Flyway(config: Config) extends LazyLogging {
  def migration(): Unit = {

    val jdbcUrl  = config.getString("url")
    val username = config.getString("username")
    val password = config.getString("password")
    val flyway = org.flywaydb.core.Flyway
      .configure()
      .baselineOnMigrate(true)
      .dataSource(jdbcUrl, username, password)
      .validateOnMigrate(false)
      .load()

    // repair removes any previously failed migrations from the table and tries to run current set of migrations again.
    // without running repair Flyway just stops if it sees a failed migration
    logger.info(s"[FLYWAY MIGRATION] Repairing flyway table if needed...")
    flyway.repair()

    logger.info(s"[FLYWAY MIGRATION] Running database migration tool from: [${flyway.getConfiguration.getLocations.mkString(",")}}]...")
    // Important: do not catch exceptions - if we cannot execute a migration,
    // the DB may be in inconsistent state/cannot be used with current code version and we need to resolve this manually.
    flyway.migrate()

    logger.info("[FLYWAY MIGRATION] Database migration is done...")
  }

}
