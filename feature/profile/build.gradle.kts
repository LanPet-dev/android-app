plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.convention.feature)
    alias(libs.plugins.convention.ui.compose)
    alias(libs.plugins.convention.lib.build)
    alias(libs.plugins.convention.lib.coil)
    alias(libs.plugins.android.junit5)
}

android {
    namespace = "com.lanpet.profile"

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(projects.core.auth)
    implementation(projects.domain.model)
    implementation(projects.domain.usecase)
    implementation(libs.androidx.lifecycle.viewmodel.android)
    implementation(libs.androidx.animation.core)
    implementation(libs.androidx.animation.core)
    implementation(libs.androidx.animation.core)
}
