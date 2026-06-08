plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "id.lumut.democleanarch.core.common"
    compileSdk = 36

    defaultConfig {
        minSdk = 29
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

// Core Common — module SHARED untuk semua feature.
// Berisi utility, extension, wrapper yang dipakai bersama.
// TIDAK bergantung ke feature module manapun.
dependencies {
    testImplementation(libs.junit)
}
