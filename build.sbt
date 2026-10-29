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

lazy val core = project
  .in(file("core"))
  .settings(commonSettings)
  .settings(MyVersions.coreLib)
  .settings(MyVersions.coreTest)
  .settings(
    name        := "slayer",
    description := "N-arity, multi-param-list, generics-aware ZIO service stubbing",
  )

lazy val root = project
  .in(file("."))
  .aggregate(core)
  .settings(
    name           := "slayer-root",
    publish / skip := true,
  )
