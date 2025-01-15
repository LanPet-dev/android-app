plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.convention.lib.build)
    alias(libs.plugins.convention.ui.compose)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.convention.lib.coil)
    alias(libs.plugins.convention.lib.junit5)
    alias(libs.plugins.android.junit5)
}

android {
    namespace = "com.lanpet.core.common"
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.core.manager)
    implementation(projects.domain.model)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    androidTestImplementation(libs.androidx.espresso.core)
}
