import play.core.PlayVersion.current
import sbt._

object AppDependencies {

  val compile = Seq(
    "uk.gov.hmrc"           %% "bootstrap-backend-play-28"  % "5.1.0",
    "uk.gov.hmrc"           %% "play-language"              % "4.12.0-play-28",
    "com.github.tototoshi"  %% "scala-csv"                  % "1.3.7"
  )

  val test = Seq(
    "uk.gov.hmrc"             %% "bootstrap-test-play-28" % "5.1.0"         % Test,
    "org.scalatest"           %% "scalatest"              % "3.2.8"         % Test,
    "org.scalatestplus.play"  %% "scalatestplus-play"     % "5.1.0"         % Test,
    "com.typesafe.play"       %% "play-test"              % current         % Test,
    "com.vladsch.flexmark"    %  "flexmark-all"           % "0.36.8"        % Test,
    "org.assertj"             %  "assertj-core"           % "3.19.0"        % Test
  )

}
