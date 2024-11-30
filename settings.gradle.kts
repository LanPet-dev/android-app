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
include(":feature:landing")
include(":feature:auth")
include(":feature:profile")
include(":feature:myprofile")
include(":feature:free")
include(":feature:wiki")

// data
include(":data:repository")
include(":data:dto")
include(":data:service")

// domain
include(":domain:usecase")
include(":domain:repository")
include(":domain:model")

// core
include(":core:navigation")
include(":core:designsystem")
include(":core:di")
include(":core:auth")
include(":core:common")
include(":app")

// build-logic
includeBuild("build-logic")
