val versionCompose: String by project

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {

    compileSdk = 31

    defaultConfig {

        applicationId = "com.keygenqt.android_kchat"

        minSdk = 21
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
        kotlinCompilerExtensionVersion = versionCompose
    }
}


dependencies {
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.compose.ui:ui:$versionCompose")
    implementation("androidx.compose.material:material:$versionCompose")
    implementation("androidx.compose.ui:ui-tooling-preview:$versionCompose")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-compose:1.3.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$versionCompose")
    debugImplementation("androidx.compose.ui:ui-tooling:$versionCompose")
}