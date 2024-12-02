plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.convention.lib.build)
    alias(libs.plugins.convention.ui.compose)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.lanpet.core.navigation"

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.core.common)
    implementation(projects.feature.auth)
    implementation(projects.feature.myposts)
    implementation(projects.feature.settings)
    implementation(projects.feature.landing)
    implementation(projects.feature.wiki)
    implementation(projects.feature.profile)
    implementation(projects.feature.free)
    implementation(projects.feature.myprofile)
    implementation(projects.domain.model)
    implementation(projects.core.auth)
    implementation(projects.data.dto)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.androidx.navigation.testing)
}