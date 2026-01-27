import sbt.*

object AppDependencies {

  val bootstrapVersion = "10.5.0"

  val compile: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"           %% "bootstrap-backend-play-30"  % bootstrapVersion,
    "com.github.tototoshi"  %% "scala-csv"                  % "2.0.0"
  )

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"           %% "bootstrap-test-play-30"     % bootstrapVersion,
    "org.assertj"           % "assertj-core"                % "3.27.7"
  ).map(_ % Test)

}
