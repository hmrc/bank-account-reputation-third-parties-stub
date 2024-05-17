import sbt.*

object AppDependencies {
  val bootstrapVersion = "8.6.0"

  val compile: Seq[ModuleID] = Seq(
    "uk.gov.hmrc" %% "bootstrap-backend-play-30" % bootstrapVersion,
    "com.github.tototoshi" %% "scala-csv" % "1.3.10"
  )

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc" %% "bootstrap-test-play-30" % bootstrapVersion % Test,
    "org.assertj" % "assertj-core" % "3.23.1" % Test
  )

}
