plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.convention.feature)
    alias(libs.plugins.convention.ui.compose)
    alias(libs.plugins.convention.lib.build)
}

android {
    namespace = "com.example.auth"

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(projects.domain.model)
    implementation(projects.domain.usecase)
    implementation(projects.domain.repository)

    implementation(libs.androidx.lifecycle.viewmodel.android)
    implementation(project(":core:auth"))
}