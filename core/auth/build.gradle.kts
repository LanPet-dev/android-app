import org.gradle.kotlin.dsl.android

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.convention.lib.build)
    alias(libs.plugins.convention.ui.compose)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.convention.feature)
    alias(libs.plugins.android.junit5)
    alias(libs.plugins.convention.lib.junit5)
}


android {
    namespace = "com.lanpet.core.auth"


    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.lifecycle.viewmodel.android)
    implementation(projects.domain.model)
    implementation(projects.domain.usecase)
    implementation(projects.core.manager)
    implementation(projects.core.testing)
    implementation(projects.domain.model)

    implementation(libs.androidx.browser)
    //google auth
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.mockk.android)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.hilt.testing)
    testImplementation(libs.turbine)
    testImplementation(projects.domain.repository)
}