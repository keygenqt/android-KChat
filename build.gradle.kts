buildscript {

    val kotlinVersion: String by project
    val gradleVersion: String by project
    val hiltCoreVersion: String by project

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:$gradleVersion")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hiltCoreVersion")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}