plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.convention.lib.build)
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.convention.lib.retrofit)
    alias(libs.plugins.convention.lib.junit5)
}

android {
    namespace = "com.lanpet.data.dto"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.domain.model)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.androidx.room.testing)
    androidTestImplementation(libs.androidx.espresso.core)
}
