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
// Feature Layered Base Multi Module (Philipp Lackner — Runique)
// ═══════════════════════════════════════════════════════════════
//
// Struktur (mengikuti github.com/philipplackner/Runique):
//
//   :app                          → Entry point, DI wiring
//   :core:domain                  → Shared domain (User, AppResult)
//   :auth:domain                  → Auth business logic
//   :auth:data                    → Auth data source
//   :auth:presentation            → Auth UI (Compose)
//   :home:domain                  → Home business logic
//   :home:data                    → Home data source
//   :home:presentation            → Home UI (Compose)
//
// Aturan Philipp Lackner:
//   ✅ Feature module boleh depend ke :core:domain
//   ✅ :data boleh depend ke :domain (implements interface)
//   ✅ :presentation boleh depend ke :domain (uses UseCase)
//   ❌ Feature TIDAK BOLEH depend ke feature lain langsung
//   ❌ :domain TIDAK BOLEH depend ke :data atau :presentation
//
// ═══════════════════════════════════════════════════════════════

include(":app")

// Core modules (shared lintas feature)
include(":core:domain")

// Feature: Auth
include(":auth:presentation")
include(":auth:domain")
include(":auth:data")

// Feature: Home
include(":home:presentation")
include(":home:domain")
include(":home:data")