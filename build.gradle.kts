// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.jetbrains.serialization) apply false
    alias(libs.plugins.android.junit5) apply false
    alias(libs.plugins.ktlint) apply true
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint") // Version should be inherited from parent

    dependencies {
        add("ktlintRuleset", "io.nlopez.compose.rules:ktlint:0.4.22")
    }

    ktlint {
        version.set("1.4.1")
        debug.set(true)
        verbose.set(true)
        android.set(true)
        outputToConsole.set(true)
        reporters {
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
        }
        filter {
            exclude("**/generated/**")
            include("**/kotlin/**")
        }
    }

    // 빌드 전에 ktlint 검사 실행
    tasks.matching { it.name.contains("build") }.configureEach {
        dependsOn("ktlintCheck")
    }
}
