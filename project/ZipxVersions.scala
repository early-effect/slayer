import zipx.*

/** Inbound rows and the one outbound ship. sbt-zipx and sbt-pgp are not rows: zipx emits itself and brings pgp.
  */
object MyVersions extends ZipxVersions:
  val sbt: SbtVersion     = SbtVersion("2.1.0-RC3")
  val scala: ScalaVersion = ScalaVersion("3.10.1")

  val slayer = Ship("core", "0.2.0")

  val zio        = Lib("dev.zio", "zio", "2.1.27")
  val zioTest    = zio.mod("zio-test").test
  val zioTestSbt = zio.mod("zio-test-sbt").test
  // The compiler jar is JVM-only. Java cross keeps the `_3` artifact from picking up a Native suffix.
  val compiler   = Lib("org.scala-lang", "scala3-compiler_3", "3.10.1").java
  val scaluzzi   = Lib("com.github.vovapolu", "scaluzzi", "0.1.23")

  val scalafmt    = Plugin("org.scalameta", "sbt-scalafmt", "2.6.2")
  val scalafix    = Plugin("ch.epfl.scala", "sbt-scalafix", "0.14.10")
  val scoverage   = Plugin("org.scoverage", "sbt-scoverage", "2.4.4")
  val scalaNative = Plugin("org.scala-native", "sbt-scala-native", "0.5.12")

  private def provided(lib: Lib): Lib = lib.copy(config = Some("provided"))

  def coreLib  = library(provided(zio), provided(compiler))
  def coreTest = library(zioTest, zioTestSbt)
end MyVersions
