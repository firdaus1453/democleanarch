plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "id.lumut.democleanarch.feature.auth.domain"
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

// Feature Auth Domain — domain layer KHUSUS untuk feature Auth
// Hanya bergantung ke :core:common (shared utilities)
// TIDAK bergantung ke feature lain!
dependencies {
    implementation(project(":core:common"))

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}
