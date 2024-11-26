package utils

import com.android.build.api.dsl.CommonExtension
import com.example.convention.utils.libs
import org.gradle.api.JavaVersion.VERSION_17
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import java.io.FileInputStream
import java.util.Properties

internal fun Project.configureAndroidCommon(commonExtension: CommonExtension<*, *, *, *, *>) {
    // keyStore.properties 읽기
    val keyStoreProperties =
        Properties().apply {
            val localPropertiesFile = rootProject.file("keyStore.properties")
            if (localPropertiesFile.exists()) {
                load(FileInputStream(localPropertiesFile))
            }
        }

    val properties = Properties().apply {
        load(project.rootProject.file("local.properties").inputStream())
    }

    commonExtension.apply {
        compileSdk = 35
        defaultConfig {
            minSdk = 24
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

//            resValue("string", "GOOGLE_OAUTH_CLIENT_KEY", properties.getProperty("GOOGLE_OAUTH_CLIENT_KEY"))
            buildConfigField("String", "GOOGLE_OAUTH_CLIENT_KEY", "${properties.getProperty("GOOGLE_OAUTH_CLIENT_KEY")}")
        }

        buildFeatures {
            buildConfig = true
        }

        signingConfigs {
            create("debug.key") {
                storeFile = file(keyStoreProperties.get("debug.keystore") as String)
                storePassword = keyStoreProperties.get("debug.storePassword") as String
                keyAlias = keyStoreProperties.get("debug.keyAlias") as String
                keyPassword = keyStoreProperties.get("debug.keyPassword") as String
            }
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
    configurePlugins()
}

// common dependencies for configure
internal fun Project.configureDependencies() {
    dependencies {
        "implementation"(libs.findLibrary("kotlinx.serialization.json").get())
        // TODO("Satoshi"): Add common dependencies
    }
}

internal fun Project.configurePlugins() {
    with(plugins) {
        apply(
            libs
                .findPlugin("jetbrains.serialization")
                .get()
                .get()
                .pluginId,
        )
    }
}

// Kotlin options 설정을 위한 확장 함수
internal fun CommonExtension<*, *, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}
