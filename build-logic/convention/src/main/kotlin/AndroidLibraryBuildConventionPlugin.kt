import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import utils.configureAndroidCommon
import java.util.Properties

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
                        isMinifyEnabled = false
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro",
                        )
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
