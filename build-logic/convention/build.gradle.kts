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
        register("androidApplicationBuild"){
            id = "convention.andriod.app.build"
            implementationClass = "AndroidApplicationBuildConventionPlugin"
        }
        register("androidLibraryBuild"){
            id = "convention.andriod.lib.build"
            implementationClass = "AndroidLibraryBuildConventionPlugin"
        }
    }
}