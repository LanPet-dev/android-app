import org.gradle.kotlin.dsl.android

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.convention.feature)
    alias(libs.plugins.convention.ui.compose)
    alias(libs.plugins.convention.lib.build)
    alias(libs.plugins.convention.lib.coil)
    alias(libs.plugins.convention.lib.junit5)
}

android {
    namespace = "com.lanpet.free"

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(projects.core.auth)
    implementation(projects.domain.model)
    implementation(projects.domain.usecase)
    implementation(libs.androidx.lifecycle.viewmodel.android)
    testImplementation(libs.kotlinx.coroutines.test)
}
