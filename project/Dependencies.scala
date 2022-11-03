import sbt._

object Dependencies {

  private object Cats {
    private lazy val version = "2.8.0"
    private lazy val namespace = "org.typelevel"
    lazy val core = namespace %% "cats-core" % version
  }

  private object PureConfig {
    private lazy val version = "0.17.1"
    private lazy val namespace = "com.github.pureconfig"
    lazy val pureconfig = namespace %% "pureconfig" % version
  }

  private object Logging {
    lazy val scalaLogging =
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5"
    lazy val logBack = "ch.qos.logback" % "logback-classic" % "1.4.4"
  }

  private object Postgres {
    private lazy val version = "42.5.0"
    private lazy val namespace = "org.postgresql"
    lazy val postgresql = namespace % "postgresql" % version
  }

  private object Slick {
    private lazy val version = "3.4.1"
    private lazy val namespace = "com.typesafe.slick"
    lazy val slick = namespace %% "slick" % version
    lazy val hikaricp = namespace %% "slick-hikaricp" % version
  }

  private object Flyway {
    private val version = "9.6.0"
    private val namespace = "org.flywaydb"
    lazy val flyway = namespace % "flyway-core" % version
  }

  private object ScalaTest {
    lazy val test = "org.scalatest" %% "scalatest" % "3.2.14" % Test
    lazy val check = "org.scalacheck" %% "scalacheck" % "1.17.0" % Test
    lazy val checkPlus =
      "org.scalatestplus" %% "scalacheck-1-17" % "3.2.14.0" % Test
  }

  private object Mockito {
    lazy val core = "org.mockito" %% "mockito-scala" % "1.17.12" % Test
  }

  private object H2 {
    private val version = "2.1.214"
    private val namespace = "com.h2database"
    lazy val h2 = namespace % "h2" % version % Test
  }

  object Application {
    val dependencies: Seq[ModuleID] = Seq(
      Cats.core,
      PureConfig.pureconfig,
      Logging.logBack,
      Logging.scalaLogging,
      Postgres.postgresql,
      Slick.slick,
      Slick.hikaricp,
      Flyway.flyway,
      ScalaTest.test,
      ScalaTest.check,
      ScalaTest.checkPlus,
      Mockito.core,
      H2.h2
    )
  }
}
