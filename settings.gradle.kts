pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "democleanarch"

// ═══════════════════════════════════════════════════
// Multi Module Architecture — 3 Layer
// ═══════════════════════════════════════════════════
// :app     → Presentation Layer (UI, ViewModel)
// :domain  → Domain Layer (Model, UseCase, Repository Interface)
// :data    → Data Layer (Repository Implementation)
// ═══════════════════════════════════════════════════
include(":app")
include(":domain")
include(":data")