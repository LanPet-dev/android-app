import org.gradle.kotlin.dsl.android
import java.util.Properties

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

    buildFeatures {
        buildConfig = true
    }

    val localProperties =
        Properties().apply {
            load(project.rootProject.file("local.properties").inputStream())
        }

    defaultConfig {
        buildConfigField(
            "String",
            "GOOGLE_OAUTH_CLIENT_KEY",
            localProperties.getProperty("GOOGLE_OAUTH_CLIENT_KEY"),
        )
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
    // google auth
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.hilt.testing)
    testImplementation(projects.domain.repository)
}
