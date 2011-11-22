import sbt._
import com.github.siasia._
import WebPlugin._
import Keys._

object WebLibBuild extends Build {
  
  override lazy val settings = super.settings ++ buildSettings
  def buildSettings = Seq(
    scalaVersion := "2.9.0" //,
    //scalaSource in Compile <<= baseDirectory {(base: File) => base / "src"}
  )
  
  
  lazy val root = Project(id = "root",
    base = file(".")) aggregate (lib, web)

  lazy val web =  Project(id = "web",
    base = file("web")) dependsOn(lib)

  lazy val lib = Project(id ="lib", 
    base = file("lib"))
    
    

  //lazy val publishAll = TaskKey[Unit]("publish-all")
  def rootSettings = Seq(
    run <<= inAll(seq(lib.settings), run.task)
  )
    
  


  object Dependency {
    //val scalavaadin = uri("git://github.com/ ...")
    val scalavaadin = "org.scalavaadin" %% "core" % "0.0.3"

  }
  
  def inAll(projects: => Seq[ProjectReference], key: ScopedSetting[Task[Unit]]): Project.Initialize[Task[Unit]] =
  inAllProjects(projects, key) { deps => nop dependsOn( deps : _*) }

  def inAllProjects[T](projects: => Seq[ProjectReference], key: ScopedSetting[T]): Project.Initialize[Seq[T]] =
  Project.bind( (loadedBuild, thisProjectRef).identity ) { case (lb, pr) =>
    def resolve(ref: ProjectReference): ProjectRef = Scope.resolveProjectRef(pr.build, Load.getRootProject(lb.units), ref)
    val refs = projects flatMap { base => Defaults.transitiveDependencies(resolve(base.project), lb, includeRoot=true, classpath=true, aggregate=true) }
    refs map ( ref => (key in ref).? ) joinWith(_ flatMap { x => x})
  }


}
