plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    kotlin("kapt")

    // https://dagger.dev/hilt/
    id("dagger.hilt.android.plugin")
    // https://github.com/Kotlin/kotlinx.serialization
    kotlin("plugin.serialization")
    // https://github.com/google/ksp
    id("com.google.devtools.ksp")
    // https://github.com/diffplug/spotless
    id("com.diffplug.spotless")
    // https://developers.google.com/android/guides/google-services-plugin
    id("com.google.gms.google-services")
}

spotless {
    kotlin {
        target("**/*.kt")
        licenseHeaderFile("${project.projectDir}/../spotless.license")
    }
    kotlinGradle {
        target("*.gradle.kts")
        ktlint()
    }
}

android {

    compileSdk = 31

    defaultConfig {

        applicationId = "com.keygenqt.kchat"

        minSdk = 23
        targetSdk = 31
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = project.property("composeVersion") as String
    }
}

dependencies {

    val composeVersion: String by project
    val ktorVersion: String by project
    val roomVersion: String by project
    val hiltCoreVersion: String by project
    val hiltVersion: String by project
    val hiltComposeNavigationVersion: String by project
    val timberVersion: String by project
    val startupVersion: String by project
    val coreKtxVersion: String by project
    val activityComposeVersion: String by project
    val firebaseBomVersion: String by project
    val accompanistVersion: String by project

    // base
    implementation("androidx.core:core-ktx:$coreKtxVersion")

    // ktor
    implementation("io.ktor:ktor-client-websockets:$ktorVersion")
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
    implementation("io.ktor:ktor-client-json:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization-jvm:$ktorVersion")
    implementation("io.ktor:ktor-client-logging-jvm:$ktorVersion")

    // firebase
    implementation(platform("com.google.firebase:firebase-bom:$firebaseBomVersion"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-auth-ktx")

    // room
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    // hilt
    implementation("com.google.dagger:hilt-android:$hiltCoreVersion")
    kapt("com.google.dagger:hilt-compiler:$hiltCoreVersion")
    kapt("androidx.hilt:hilt-compiler:$hiltVersion")
    implementation("androidx.hilt:hilt-navigation-compose:$hiltComposeNavigationVersion")

    // accompanist
    implementation("com.google.accompanist:accompanist-insets:$accompanistVersion")

    // compose
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.activity:activity-compose:$activityComposeVersion")

    // other
    implementation("com.jakewharton.timber:timber:$timberVersion")
    implementation("androidx.startup:startup-runtime:$startupVersion")
}
