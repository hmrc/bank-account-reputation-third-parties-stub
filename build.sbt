import uk.gov.hmrc.DefaultBuildSettings.{addTestReportOption, defaultSettings, scalaSettings}
import uk.gov.hmrc.sbtdistributables.SbtDistributablesPlugin.publishingSettings

maintainer := "txm-attribute-validation-g@digital.hmrc.gov.uk"
val appName = "bank-account-reputation-third-parties-stub"

lazy val root = Project(appName, file("."))
  .enablePlugins(
    play.sbt.PlayScala,
    SbtAutoBuildPlugin,
    SbtGitVersioning,
    SbtDistributablesPlugin
  )

  .settings(defaultSettings(): _*)
  .settings(scalaSettings: _*)
  .settings(
    majorVersion := 0,
    scalaVersion := "2.12.11",
    resolvers += Resolver.jcenterRepo,
    evictionWarningOptions in update := EvictionWarningOptions.default
      .withWarnTransitiveEvictions(false)
      .withWarnDirectEvictions(false)
      .withWarnScalaVersionEviction(false)
  )

  .settings(publishingSettings: _*)
  .settings(
    libraryDependencies ++= AppDependencies.compile ++ AppDependencies.test
  )

  .configs(Test)
  .settings(addTestReportOption(Test, "test-reports"))
