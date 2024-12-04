import org.gradle.kotlin.dsl.android

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.convention.lib.build)
    alias(libs.plugins.convention.ui.compose)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.convention.feature)
}



android {
    namespace = "com.lanpet.core.auth"


    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.lifecycle.viewmodel.android)
    implementation(projects.domain.model)
    implementation(projects.domain.usecase)
    coreLibraryDesugaring(libs.desugar.jdk.libs)
    // aws amplify
    implementation(libs.amplify.authenticator)

    implementation(projects.domain.model)

    //google auth
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    androidTestImplementation(libs.androidx.espresso.core)
}