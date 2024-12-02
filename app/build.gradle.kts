import java.util.Properties
import kotlin.apply

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.convention.app.build)
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

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
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
    coreLibraryDesugaring(libs.desugar.jdk.libs)
    implementation(libs.amplify.authenticator)
    implementation(libs.amplify.core)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    implementation(projects.core.navigation)
    implementation(projects.core.di)
    implementation(projects.core.auth)
    implementation(projects.core.common)
    implementation(projects.feature.auth)
    implementation(projects.feature.landing)
    implementation(projects.feature.profile)
    implementation(projects.feature.free)
    implementation(projects.feature.myprofile)
    implementation(projects.feature.wiki)
    implementation(projects.feature.myposts)
    implementation(projects.feature.settings)
    implementation(projects.core.designsystem)
    implementation(projects.domain.model)

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
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)
    implementation(libs.coil.network.okhttp)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
