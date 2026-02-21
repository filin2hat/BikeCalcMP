rootProject.name = "BikeCalcMP"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("android.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven {
            name = "kotzilla"
            url = uri("https://repository.kotzilla.io/repository/kotzilla-platform/")
        }
    }
    plugins {
        id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("android.*")
            }
        }
        mavenCentral()
        maven {
            name = "kotzilla"
            url = uri("https://repository.kotzilla.io/repository/kotzilla-platform/")
        }
    }
}

include(":composeApp")

// Core modules
include(":core:model")
include(":core:common")
include(":core:database")
include(":core:settings")

// Design system
include(":designsystem")

// Feature modules
include(":feature:pressure")
include(":feature:development")
