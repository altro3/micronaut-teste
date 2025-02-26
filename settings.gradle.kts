rootProject.name = "foo"

include(
    ":apps:items",
    ":apps:checkouts",
)

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        val kotlinVersion: String by settings
        val micronautApplicationVersion: String by settings
        val versionsVersion: String by settings

        // this list needs to be kept in sync with the plugins list in build.gradle.kts
        kotlin("jvm") version kotlinVersion
        kotlin("kapt") version kotlinVersion
        kotlin("plugin.allopen") version kotlinVersion
        kotlin("plugin.noarg") version kotlinVersion
        id("io.micronaut.application") version micronautApplicationVersion
        id("com.github.ben-manes.versions") version versionsVersion
        application
    }
}
