import sbt.{Def, _}
import sbt.Keys._
import sbtassembly.AssemblyKeys.mergeStrategy
import sbtassembly.{MergeStrategy, PathList}

object ProjectSettings {

  def BaseProject(name: String): Project = Project(name, file(name))
    .settings(
      Defaults.coreDefaultSettings ++
        Seq(
          organization := "com.knoldus",
          scalaVersion in ThisBuild := "2.11.8",
          version := "1.0.0",
          fork in Test := true,
          fork in IntegrationTest := true,
          parallelExecution in IntegrationTest := false,
          parallelExecution in Test := false,
//          scalacOptions += "-Ylog-classpath",
          projectResolvers
        ))

  def projectResolvers: Def.Setting[Seq[Resolver]] = {
    resolvers ++= Seq(
      Resolver.sonatypeRepo("releases"),
      Resolver.sonatypeRepo("snapshots"),
      "Twitter Maven" at "https://maven.twttr.com",
      "Finatra Repo" at "http://twitter.github.com/finatra",
      "Typesafe Repository" at "https://repo.typesafe.com/typesafe/releases/",
      "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
      "Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases/",
      "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
    )
  }

}
