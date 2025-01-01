import org.gradle.kotlin.dsl.android
import org.gradle.kotlin.dsl.hilt

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.convention.lib.build)
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.convention.lib.retrofit)
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.lanpet.core.di"
}

dependencies {
    implementation(libs.hilt.android)
    implementation(projects.data.dto)
    implementation(projects.domain.model)
    implementation(projects.core.manager)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(projects.core.navigation)
    implementation(projects.core.auth)
    implementation(projects.data.service)
    implementation(projects.domain.repository)
    implementation(projects.data.repository)
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.androidx.room.testing)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.hilt.testing)
}
