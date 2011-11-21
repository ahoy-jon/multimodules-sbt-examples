import sbt._
import Keys._

object WebLibBuild extends Build {
  lazy val root = Project(id = "root",
    base = file(".")) aggregate (lib, web)

  lazy val web =  Project(id = "web",
    base = file("web"))  dependsOn(lib)

  lazy val lib = Project(id ="lib", 
    base = file("lib"))


  object Dependency {
    //val scalavaadin = uri("git://github.com/ ...")
    val scalavaadin = "org.scalavaadin" %% "core" % "0.0.3"

  }

}
