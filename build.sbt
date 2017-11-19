name := "circumflex-ftl"

organization := "ru.circumflex"

version := "2.1.1"

scalaVersion := "2.12.4"

scalacOptions ++= Seq("-deprecation", "-unchecked")

resolvers ++= Seq ("scala-tools snapshots" at "http://scala-tools.org/repo-snapshots")

publishTo := Some(Resolver.file("Github Pages", Path.userHome / "projects" / "maven2" asFile) (Patterns(true, Resolver.mavenStyleBasePattern)))

publishMavenStyle := true

libraryDependencies ++= Seq(
  "org.freemarker"   		% 	"freemarker"  			% "2.3.18",
  "net.liftweb"         	%% 	"lift-util"          	% "3.1.1" 	% "compile",
  "net.liftweb"         	%% 	"lift-common"          	% "3.1.1" 	% "compile",
  "commons-beanutils"   	% 	"commons-beanutils"     % "1.9.3",
  "commons-io"   			% 	"commons-io"      		% "2.6",
  "org.apache.commons"   	% 	"commons-lang3"      	% "3.7" 			% "test",
  "org.scalatest" %% "scalatest" % "3.0.4" % "test"
)