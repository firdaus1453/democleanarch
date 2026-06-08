plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "id.lumut.democleanarch.feature.auth.data"
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

// Feature Auth Data — data layer KHUSUS untuk feature Auth
// Bergantung ke:
// - :feature:auth:domain (implements interface dari domain)
// - :core:common (shared utilities)
dependencies {
    implementation(project(":feature:auth:domain"))
    implementation(project(":core:common"))

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}
