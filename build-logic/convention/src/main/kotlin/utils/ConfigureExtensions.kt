package utils

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion.VERSION_17
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions


internal fun Project.configureAndroidCommon(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = 35

        defaultConfig {
            minSdk = 24
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        compileOptions {
            sourceCompatibility = VERSION_17
            targetCompatibility = VERSION_17
        }


        // Kotlin options 설정
        (this as ExtensionAware).extensions.configure<KotlinJvmOptions>("kotlinOptions") {
            jvmTarget = VERSION_17.majorVersion.toString()
        }
    }

    configureDependencies()
}


// common dependencies for configure
internal fun Project.configureDependencies() {
    dependencies {
//        "implementation"("androidx.core:core-ktx:1.12.0")
//        "implementation"("androidx.appcompat:appcompat:1.6.1")
        //TODO("Satoshi"): Add common dependencies
    }
}

// Kotlin options 설정을 위한 확장 함수
internal fun CommonExtension<*, *, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}