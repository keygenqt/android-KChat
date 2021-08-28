plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    kotlin("kapt")

    // https://dagger.dev/hilt/
    id("dagger.hilt.android.plugin")
    // https://github.com/Kotlin/kotlinx.serialization
    kotlin("plugin.serialization")
    // https://github.com/diffplug/spotless
    id("com.diffplug.spotless")
    // https://developers.google.com/android/guides/google-services-plugin
    id("com.google.gms.google-services")
    // https://firebase.google.com/products/crashlytics
    id("com.google.firebase.crashlytics")
    // https://github.com/ben-manes/gradle-versions-plugin
    id("com.github.ben-manes.versions") version "0.39.0"
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

        minSdk = 26
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
    // division resources
    sourceSets {
        getByName("main").let { data ->
            data.res.setSrcDirs(emptySet<String>())
            file("src/main/res").listFiles()?.toList()?.forEach { dir ->
                data.res.srcDir(dir)
            }
        }
    }
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
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
    val gsonVersion: String by project
    val constraintLayoutComposeVersion: String by project
    val securityCryptoVersion: String by project
    val coroutinesPlayServicesVersion: String by project
    val pagingComposeVersion: String by project

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
    implementation("com.google.firebase:firebase-perf-ktx")
    implementation("com.google.firebase:firebase-config-ktx")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-crashlytics")

    // room
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    // hilt
    implementation("com.google.dagger:hilt-android:$hiltCoreVersion")
    kapt("com.google.dagger:hilt-compiler:$hiltCoreVersion")
    kapt("androidx.hilt:hilt-compiler:$hiltVersion")
    implementation("androidx.hilt:hilt-navigation-compose:$hiltComposeNavigationVersion")

    // accompanist
    implementation("com.google.accompanist:accompanist-insets:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-pager:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-swiperefresh:$accompanistVersion")

    // compose
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.constraintlayout:constraintlayout-compose:$constraintLayoutComposeVersion")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.activity:activity-compose:$activityComposeVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")
    implementation("androidx.paging:paging-compose:$pagingComposeVersion")

    // other
    implementation("com.jakewharton.timber:timber:$timberVersion")
    implementation("androidx.startup:startup-runtime:$startupVersion")
    implementation("com.google.code.gson:gson:$gsonVersion")
    implementation("androidx.security:security-crypto:$securityCryptoVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$coroutinesPlayServicesVersion")
}
