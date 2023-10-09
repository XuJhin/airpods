pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "airpods"

include(":lib")
include(":lib:redis")
include(":lib:common")
include(":services")
include("services:gateway")
include("services:user")
include("services:community")
