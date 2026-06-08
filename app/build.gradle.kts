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

// ═══════════════════════════════════════════════════════════
// :app — ENTRY POINT saja (hanya MainActivity + DI wiring)
// ═══════════════════════════════════════════════════════════
// :app tahu semua module karena dia yang melakukan wiring.
// Tapi TIDAK ada business logic atau UI di sini.
//
// Dependency graph:
//   :app → :presentation (UI)
//   :app → :domain       (UseCase, Model)
//   :app → :data         (Repository impl untuk DI)
//
//   :presentation → :domain (uses UseCase)
//   :data         → :domain (implements interface)
//   :domain       → (nothing) INDEPENDENT
// ═══════════════════════════════════════════════════════════
dependencies {
    implementation(project(":presentation"))
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.material)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3)
}