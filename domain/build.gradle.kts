plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "id.lumut.democleanarch.domain"
    compileSdk = 36

    defaultConfig {
        minSdk = 29
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
    }
}

// Domain module TIDAK PUNYA dependency ke module lain!
// Ini adalah inti dari Clean Architecture:
// Domain layer adalah yang paling independent.
dependencies {
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}
