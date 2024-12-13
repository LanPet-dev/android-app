plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.convention.lib.build)
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.convention.lib.retrofit)
}

android {
    namespace = "com.lanpet.data.service"
}

dependencies {
    implementation(projects.domain.model)
    implementation(projects.data.dto)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(projects.core.manager)
    androidTestImplementation(libs.androidx.espresso.core)
}
