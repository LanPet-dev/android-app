plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.convention.feature)
    alias(libs.plugins.convention.lib.junit5)
    alias(libs.plugins.convention.ui.compose)
    alias(libs.plugins.convention.lib.build)
    alias(libs.plugins.convention.lib.coil)
}

android {
    namespace = "com.lanpet.feature.myposts"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.auth)
    implementation(projects.domain.model)
    implementation(projects.domain.usecase)
    implementation(libs.androidx.lifecycle.viewmodel.android)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.jupiter.junit.jupiter)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.hilt.testing)
    androidTestImplementation(libs.androidx.espresso.core)
}
