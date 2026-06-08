plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "id.lumut.democleanarch.core.domain"
    compileSdk = 36

    defaultConfig {
        minSdk = 29
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

// :core:domain — shared domain code (Philipp Lackner style)
// Berisi model & utility yang dipakai LINTAS feature.
// TIDAK bergantung ke feature module manapun.
dependencies {
    testImplementation(libs.junit)
}
