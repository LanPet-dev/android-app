plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.convention.lib.build)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.lanpet.domain.usecase"
}

dependencies {
    implementation(projects.data.repository)
    implementation(projects.domain.repository)
    implementation(projects.domain.model)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    androidTestImplementation(libs.androidx.espresso.core)
}