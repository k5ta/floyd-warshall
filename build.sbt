lazy val root =
  Project("FloydWarshall", file("."))
    .settings(
      version := "1.0",
      scalaVersion := "2.13.3",
      libraryDependencies ++= Seq(
        "com.typesafe" % "config" % "1.4.0",
        "org.apache.logging.log4j" %% "log4j-api-scala" % "12.0",
        "org.apache.logging.log4j" % "log4j-core" % "2.13.3",

        "org.scala-lang.modules" %% "scala-parallel-collections" % "0.2.0"
      )
    )