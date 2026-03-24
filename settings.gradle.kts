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

rootProject.name = "AvitoTraineeAssignment"
include(":app")
include(":core:ui")
include(":feature:auth:api")
include(":feature:auth:impl")
include(":core:navigation")
include(":feature:chats:api")
include(":feature:chats:impl")
include(":core:database")
include(":core:network")
include(":feature:chat:api")
include(":feature:chat:impl")
include(":feature:profile:api")
include(":core:common")
include(":feature:profile:impl")
