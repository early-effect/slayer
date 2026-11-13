import scala.scalanative.sbtplugin.ScalaNativePlugin.autoImport.*

// ZIO's run loop overflows Native's default 1 MB thread stack on macOS. 8 MB matches the Linux default.
lazy val nativeThreads: Seq[sbt.Setting[?]] = Seq(
  nativeConfig ~= (_.withMultithreading(true)),
  Test / envVars += "SCALANATIVE_THREAD_STACK_SIZE" -> "8m",
)

val scala3Version: String = MyVersions.scala

lazy val nativeCiSetup: Steps = Steps.built("slayer-native-ci")(
  Step
    .run(
      Script(
        ShExec("sudo", Word.lit("apt-get"), Word.lit("update")) &&
          ShExec.of(
            "sudo",
            List(
              Word.lit("apt-get"),
              Word.lit("install"),
              Word.lit("-y"),
              Word.lit("clang"),
              Word.lit("libstdc++-12-dev"),
              Word.lit("libgc-dev"),
              Word.lit("libunwind-dev"),
            ),
          )
      )
    )
    .named("Install Scala Native build dependencies")
)

MyVersions.settings

organization         := "rocks.earlyeffect"
organizationName     := "Early Effect"
organizationHomepage := Some(uri("https://www.earlyeffect.rocks"))
licenses             := List("Apache-2.0" -> uri("http://www.apache.org/licenses/LICENSE-2.0.txt"))
homepage             := Some(uri("https://github.com/early-effect/slayer"))
scmInfo              := Some(
  ScmInfo(
    uri("https://github.com/early-effect/slayer"),
    "scm:git@github.com:early-effect/slayer.git",
  )
)
developers := List(
  Developer(
    id = "russwyte",
    name = "Russ White",
    email = "356303+russwyte@users.noreply.github.com",
    url = uri("https://github.com/russwyte"),
  )
)
versionScheme := Some("early-semver")

// The release job sets PGP_KEY_HEX. The sentinel keeps a local session loadable.
usePgpKeyHex(sys.env.getOrElse("PGP_KEY_HEX", "MISSING_KEY_HEX"))

zipxJavaVersion      := JdkVersion("25")
zipxWorkflowDispatch := true
zipxCapabilities ++= Seq(
  Capability.testAffected(onPush = false).plusExtraSteps(nativeCiSetup),
  ZipxCentral.snapshots,
  ZipxCentral.pullRequestSnapshots("snapshots"),
)
zipxReleaseWorkflow  := Some(ZipxCentral.releases)
zipxCoverageWorkflow := Some(
  Coverage.workflow(CoverageTrigger.Dispatch, CoverageTrigger.prLabel("coverage"))
)

lazy val commonSettings = Seq(
  scalacOptions ++= Seq(
    "-deprecation",
    "-Wunused:all",
    "-feature",
  ),
  testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"),
  scalafixDependencies += MyVersions.moduleID(MyVersions.scaluzzi),
  coverageFailOnMinimum := true,
  coverageExcludedFiles := ".*\\bMacros",
)

lazy val core = (projectMatrix in file("core"))
  .settings(commonSettings)
  .settings(MyVersions.coreLib)
  .settings(MyVersions.coreTest)
  .settings(
    name        := "slayer",
    description := "N-arity, multi-param-list, generics-aware ZIO service stubbing",
  )
  .jvmPlatform(scalaVersions = Seq(scala3Version))
  .nativePlatform(scalaVersions = Seq(scala3Version), nativeThreads)

lazy val root = project
  .in(file("."))
  .aggregate(core.projectRefs *)
  .settings(
    name           := "slayer-root",
    publish / skip := true,
  )
