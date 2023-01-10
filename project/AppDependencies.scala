import play.core.PlayVersion.current
import sbt._

object AppDependencies {

  val compile = Seq(
    "uk.gov.hmrc"           %% "bootstrap-backend-play-28"  % "7.12.0",
    "com.github.tototoshi"  %% "scala-csv"                  % "1.3.10"
  )

  val test = Seq(
    "uk.gov.hmrc"             %% "bootstrap-test-play-28" % "7.12.0"        % Test,
    "org.scalatest"           %% "scalatest"              % "3.2.14"        % Test,
    "org.scalatestplus.play"  %% "scalatestplus-play"     % "5.1.0"         % Test,
    "com.typesafe.play"       %% "play-test"              % current         % Test,
    "com.vladsch.flexmark"    %  "flexmark-all"           % "0.62.2"        % Test,
    "org.assertj"             %  "assertj-core"           % "3.23.1"        % Test
  )

}
