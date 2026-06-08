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

// ═══════════════════════════════════════════════════════════════
// Feature Layered Base Multi Module (Philipp Lackner style)
// ═══════════════════════════════════════════════════════════════
//
// Struktur:
//   app/                              → Main app (orchestrator, DI wiring)
//   feature/auth/presentation/        → Auth UI (Compose)
//   feature/auth/domain/              → Auth business logic
//   feature/auth/data/                → Auth data source
//   core/common/                      → Shared utilities
//
// Aturan:
//   ✅ Feature module boleh depend ke :core:common
//   ✅ :data boleh depend ke :domain (implements interface)
//   ✅ :presentation boleh depend ke :domain (uses UseCase)
//   ❌ Feature TIDAK BOLEH depend ke feature lain langsung
//   ❌ :domain TIDAK BOLEH depend ke :data atau :presentation
//
// ═══════════════════════════════════════════════════════════════

include(":app")

// Core modules
include(":core:common")

// Feature: Auth
include(":feature:auth:presentation")
include(":feature:auth:domain")
include(":feature:auth:data")