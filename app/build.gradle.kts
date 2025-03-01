plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.convention.app.build)
    alias(libs.plugins.convention.lib.junit5)
    alias(libs.plugins.convention.lib.coil)
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.lanpet.app"
    version = "1.0.0"

    defaultConfig {
        applicationId = "com.lanpet.app"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.browser)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    implementation(projects.core.navigation)
    implementation(projects.core.di)
    implementation(projects.core.manager)
    implementation(projects.core.auth)
    implementation(projects.core.common)
    implementation(projects.core.testing)
    implementation(projects.core.config)
    implementation(projects.feature.auth)
    implementation(projects.feature.landing)
    implementation(projects.feature.profile)
    implementation(projects.feature.free)
    implementation(projects.feature.myprofile)
    implementation(projects.feature.wiki)
    implementation(projects.feature.splash)
    implementation(projects.feature.myposts)
    implementation(projects.feature.settings)
    implementation(projects.core.designsystem)
    implementation(projects.domain.model)
    implementation(projects.domain.repository)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
