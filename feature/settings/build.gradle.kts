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
    namespace = "com.lanpet.feature.settings"

    defaultConfig {
        minSdk = 24
    }
}

dependencies {
    implementation(projects.domain.model)

    implementation(projects.core.common)
    implementation(projects.core.auth)
    implementation(projects.domain.model)
    implementation(libs.androidx.lifecycle.viewmodel.android)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    androidTestImplementation(libs.androidx.espresso.core)
}