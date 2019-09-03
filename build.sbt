name := "alpakka-cassandra-sample"

version := "1.0"

scalaVersion := "2.12.7"


import Dependencies._
import ProjectSettings._


lazy val commonUtil = BaseProject("common-util")
  .settings(
    libraryDependencies ++= compileDependencies(akkaHttp.value ++ akka.value ++ json4sNative.value ++ logback.value ++
      typesafeConfig.value ++ slf4j.value ++ log4j.value ++ logback.value ++ json4sNative.value ++ json4sEx.value ++
      jodaDate.value ++ alpakka.value)
    ++ testDependencies(mockito.value ++ scalaTest.value ++ spec2.value),
  parallelExecution in Test := false
)


lazy val core = BaseProject("core")
  .settings(
    libraryDependencies ++= compileDependencies(Nil)
      ++ testDependencies(Nil),
    parallelExecution in Test := false
  )
  .dependsOn(commonUtil)


lazy val processor = BaseProject("processor")
  .settings(
    libraryDependencies ++= compileDependencies(Nil)
      ++ testDependencies(Nil),
    parallelExecution in Test := false
  )
  .dependsOn(commonUtil, core)


lazy val generator = BaseProject("generator")
  .settings(
    libraryDependencies ++= compileDependencies(Nil)
      ++ testDependencies(Nil),
    parallelExecution in Test := false
  )
  .dependsOn(commonUtil, core)

