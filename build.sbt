import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

name := "hello-minart"

version := "1.0"

ThisBuild / scalaVersion := "3.7.3"

lazy val root =
  crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .in(file("."))
    .settings(
      Seq(
        libraryDependencies ++= List(
          "eu.joaocosta" %%% "minart" % "0.6.5",
          "dev.zio" %%% "zio" % "2.1.22",
          "dev.zio" %%% "zio-streams" % "2.1.22"
        )
      )
    )
    .jsSettings(
      Seq(
        scalaJSUseMainModuleInitializer := true
      )
    )
    .nativeSettings(
      Seq(
//r        nativeLinkStubs := true
        // nativeMode := "release"
        //     nativeLTO := "thin",
        // nativeGC := "commix"
      )
    )
    .settings(name := "hello-minart Root")
