plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.convention.lib.build)
    alias(libs.plugins.android.junit5)
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.lanpet.core.testing"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    androidTestImplementation(libs.androidx.espresso.core)
}