plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "id.lumut.democleanarch"
    compileSdk = 36

    defaultConfig {
        applicationId = "id.lumut.democleanarch"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
    }
}

// :app — Entry point & DI wiring (Philipp Lackner style)
// Tahu SEMUA module, tapi tidak berisi logic.
dependencies {
    // Core
    implementation(project(":core:domain"))

    // Feature: Auth
    implementation(project(":auth:presentation"))
    implementation(project(":auth:domain"))
    implementation(project(":auth:data"))

    // Feature: Home
    implementation(project(":home:presentation"))
    implementation(project(":home:domain"))
    implementation(project(":home:data"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.material)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)
}