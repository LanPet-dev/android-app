package utils

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Project

// Application 모듈용 설정
internal fun Project.configureAndroidApplication(appExtension: ApplicationExtension) {
    configureAndroidCommon(appExtension) // 공통 설정 적용

    // Application 전용 설정
    appExtension.apply {
        productFlavors {
            getByName("dev") {
                resValue("string", "app_name", "Lanpet_Dev")
                applicationIdSuffix = ".dev"
            }
            getByName("prod") {
                // use default value
            }
        }
    }
}
