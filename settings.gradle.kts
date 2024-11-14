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

rootProject.name = "LanPetApp"

// feature
include(":feature:auth")

// data
include(":data:repository")

// domain
include(":domain:usecase")
include(":domain:repository")
include(":domain:model")

// core
include(":core:navigation")
include(":core:designsystem")
include(":data:dto")
include(":app")

// build-logic
includeBuild("build-logic")
