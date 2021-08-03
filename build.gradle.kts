buildscript {

    val versionHiltCore: String by project
    val versionGoogleServices: String by project

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.google.gms:google-services:$versionGoogleServices")
        classpath("com.google.dagger:hilt-android-gradle-plugin:$versionHiltCore")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}