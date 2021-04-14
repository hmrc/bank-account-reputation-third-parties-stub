import play.core.PlayVersion.current
import sbt._

object AppDependencies {

  val compile = Seq(
    "uk.gov.hmrc"           %% "bootstrap-backend-play-27"  % "4.0.0",
    "uk.gov.hmrc"           %% "domain"                     % "5.10.0-play-27",
    "uk.gov.hmrc"           %% "play-language"              % "4.10.0-play-27",
    "com.github.tototoshi"  %% "scala-csv"                  % "1.3.6"
  )

  val test = Seq(
    "uk.gov.hmrc"             %% "bootstrap-test-play-27" % "4.0.0"   % Test,
    "org.scalatest"           %% "scalatest"              % "3.2.4"   % Test,
    "org.scalatestplus.play"  %% "scalatestplus-play"     % "3.1.2"   % Test,
    "com.typesafe.play"       %% "play-test"              % current   % Test,
    "com.vladsch.flexmark"    % "flexmark-all"            % "0.35.10" % Test,
    "org.assertj"             % "assertj-core"            % "3.19.0"  % Test
  )

}
