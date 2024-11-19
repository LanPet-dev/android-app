plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.convention.feature)
    alias(libs.plugins.convention.ui.compose)
    alias(libs.plugins.convention.app.build)
}

android {
    namespace = "com.example.auth"

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(projects.domain.model)
    implementation(projects.core.navigation)
}