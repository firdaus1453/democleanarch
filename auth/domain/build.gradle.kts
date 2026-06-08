plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "id.lumut.democleanarch.auth.domain"
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

dependencies {
    implementation(project(":core:domain"))
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}
