import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import utils.configureAndroidCommon

class AndroidLibraryBuildConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }


            extensions.configure<LibraryExtension> {
                configureAndroidCommon(this)


                buildTypes {
                    // TODO("Satoshi"): Flavors (release, debug, qa, etc...)
                    release {
                        isMinifyEnabled = true
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro",
                        )
                        consumerProguardFiles("consumer-rules.pro")
                        signingConfig = signingConfigs.getByName("debug.key")
                    }
                    debug {
                        isMinifyEnabled = false
                        signingConfig = signingConfigs.getByName("debug.key")
                    }
                }
            }
        }
    }
}
