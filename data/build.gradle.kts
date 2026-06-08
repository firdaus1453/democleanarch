plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "id.lumut.democleanarch.data"
    compileSdk = 36

    defaultConfig {
        minSdk = 29
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Data layer BERGANTUNG ke Domain layer
    // Karena data layer IMPLEMENT interface yang didefinisikan di domain
    implementation(project(":domain"))

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}
