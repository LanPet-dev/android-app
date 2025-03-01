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
    namespace = "com.lanpet.feature.splash"

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(projects.domain.model)
    implementation(projects.domain.repository)
    implementation(projects.core.auth)
    implementation(projects.feature.auth)
    implementation(projects.feature.landing)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
