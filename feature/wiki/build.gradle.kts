plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.convention.feature)
    alias(libs.plugins.convention.ui.compose)
    alias(libs.plugins.convention.lib.build)
    alias(libs.plugins.convention.lib.coil)
}

android {
    namespace = "com.lanpet.wiki"

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(projects.domain.model)
    implementation(libs.androidx.lifecycle.viewmodel.android)
}
