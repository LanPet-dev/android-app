plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.convention.feature)
    alias(libs.plugins.convention.ui.compose)
    alias(libs.plugins.convention.lib.build)
    alias(libs.plugins.convention.lib.coil)
}

android {
    namespace = "com.lanpet.myprofile"

    buildFeatures {
        compose = true
    }
}

composeCompiler {
    // Analyze stability
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    metricsDestination = layout.buildDirectory.dir("compose_compiler")
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.auth)
    implementation(projects.domain.model)
    implementation(projects.domain.usecase)
    implementation(libs.androidx.lifecycle.viewmodel.android)
}
