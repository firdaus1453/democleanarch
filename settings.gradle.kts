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
// Multi Module Architecture — 3 Layer + App
// ═══════════════════════════════════════════════════
// :app          → Entry Point (MainActivity, DI wiring saja)
// :presentation → Presentation Layer (UI, ViewModel, Theme)
// :domain       → Domain Layer (Model, UseCase, Repository Interface)
// :data         → Data Layer (Repository Implementation)
//
// Dependency:
//   :app → :presentation, :domain, :data
//   :presentation → :domain
//   :data → :domain
//   :domain → (nothing)
// ═══════════════════════════════════════════════════
include(":app")
include(":presentation")
include(":domain")
include(":data")