pluginManagement {

    val versionGradle: String by settings
    val versionKotlin: String by settings

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    plugins {
        id("com.android.application") version versionGradle
        id("com.android.library") version versionGradle
        id("org.jetbrains.kotlin.android") version versionKotlin
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "KChat"
include(":app")
