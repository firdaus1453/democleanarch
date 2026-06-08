plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // ═══════════════════════════════════════════════════════════
    // Feature Layered Multi Module Dependencies
    // ═══════════════════════════════════════════════════════════
    // :app adalah "orchestrator" — dia yang tahu SEMUA module
    // dan melakukan wiring/DI.
    //
    // Feature Auth
    implementation(project(":feature:auth:presentation"))
    implementation(project(":feature:auth:domain"))
    implementation(project(":feature:auth:data"))
    //
    // Core
    implementation(project(":core:common"))
    //
    // Jika ada feature baru (misal Home), tinggal tambah:
    // implementation(project(":feature:home:presentation"))
    // implementation(project(":feature:home:domain"))
    // implementation(project(":feature:home:data"))
    // ═══════════════════════════════════════════════════════════

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    debugImplementation(libs.androidx.ui.tooling)
}