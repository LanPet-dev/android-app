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
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "LanPetApp"

// feature
include(":feature:auth")
include(":feature:landing")

// data
include(":data:repository")

// domain
include(":domain:usecase")
include(":domain:repository")
include(":domain:model")

// core
include(":core:navigation")
include(":data:dto")
include(":app")
include(":core:designsystem")

// build-logic
includeBuild("build-logic")
