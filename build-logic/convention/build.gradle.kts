import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.example.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.gradle)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.com.google.devtools.ksp.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidUiCompose") {
            id = "convention.android.ui.compose"
            implementationClass = "AndroidUiComposeConventionPlugin"
        }
        register("androidHilt") {
            id = "convention.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidFeature") {
            id = "convention.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidApplicationBuild") {
            id = "convention.android.app.build"
            implementationClass = "AndroidApplicationBuildConventionPlugin"
        }
        register("androidLibraryBuild") {
            id = "convention.android.lib.build"
            implementationClass = "AndroidLibraryBuildConventionPlugin"
        }
        register("androidCoilLibraryBuild") {
            id = "convention.android.lib.coil"
            implementationClass = "AndroidCoilLibConventionPlugin"
        }
        register("androidRetrofitLibraryBuild") {
            id = "convention.android.lib.retrofit"
            implementationClass = "AndroidRetrofitConventionPlugin"
        }
        register("androidJunit5") {
            id = "convention.android.test.junit5"
            implementationClass = "AndroidJunit5ConventionPlugin"
        }
    }
}