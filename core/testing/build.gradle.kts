plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.convention.lib.build)
    alias(libs.plugins.android.junit5)
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.convention.lib.junit5)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.lanpet.core.testing"
}

dependencies {
    implementation(projects.domain.model)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(projects.core.common)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.mockk.android)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.hilt.testing)
    testImplementation(libs.turbine)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.android.junit5)
}