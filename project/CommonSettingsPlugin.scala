import org.scalafmt.sbt.ScalafmtPlugin.autoImport.*
import org.typelevel.sbt.tpolecat.TpolecatPlugin.autoImport.*
import org.typelevel.scalacoptions.ScalacOptions
import sbt.Keys.*
import sbt.plugins.JvmPlugin
import sbt.{io as _, *}
import scalafix.sbt.ScalafixPlugin.autoImport.scalafixOnCompile
import wartremover.WartRemover.autoImport.*

object CommonSettingsPlugin extends AutoPlugin {

  override def requires = JvmPlugin

  override def trigger = allRequirements

  override def projectConfigurations: Seq[Configuration] = Seq.empty

  override def projectSettings: Seq[Def.Setting[?]] = Seq(
    scalaVersion      := "3.3.1",
    scalafixOnCompile := true,
    version           := "0.0.1",
    organization      := "com.jfranco",
    homepage          := scmInfo.value.map(_.browseUrl),
    tpolecatScalacOptions ++= Set(
      ScalacOptions.source3,
      ScalacOptions.other(
        "-Wconf:cat=scala3-migration&msg=constructor modifiers:s"
      ),
      ScalacOptions.other("-Ymacro-annotations")
    ),
    tpolecatReleaseModeOptions ++= ScalacOptions.optimizerOptions(
      "**,!monix.newtypes.NewEncoding$Ops"
    ),
    tpolecatReleaseModeOptions += ScalacOptions.optimizerWarnings,
    tpolecatExcludeOptions                 := Set.empty,
    Compile / packageDoc / publishArtifact := false,
    wartremoverErrors ++= Warts.unsafe,
    wartremoverExcluded += sourceManaged.value,
    scalafmtPrintDiff := true,
    excludeDependencies += ExclusionRule("log4j", "log4j")
  )

//  private val disabledWarts = Seq(
//    Wart.Any,
//    Wart.DefaultArguments,
//    Wart.ImplicitParameter,
//    Wart.Nothing,
//    Wart.NonUnitStatements,
//    Wart.Overloading,
//    Wart.StringPlusAny
//  )

}
