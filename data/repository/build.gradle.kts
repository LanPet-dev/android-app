plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.convention.lib.build)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.lanpet.data.repository"
}

dependencies {
    implementation(projects.data.service)
    implementation(projects.data.dto)
    implementation(projects.domain.repository)
    implementation(projects.domain.model)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    androidTestImplementation(libs.androidx.espresso.core)
}
