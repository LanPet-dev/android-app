plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.convention.lib.build)
    alias(libs.plugins.android.junit5)
    alias(libs.plugins.convention.lib.retrofit)
}

android {
    namespace = "com.lanpet.core.manager"
}

dependencies {
    implementation(projects.domain.model)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}
