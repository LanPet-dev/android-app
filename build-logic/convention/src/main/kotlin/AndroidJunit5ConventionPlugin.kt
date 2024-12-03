import com.example.convention.utils.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidJunit5ConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("de.mannodermaus.android-junit5")
//                apply("android-junit5")
            }

            dependencies {
                add("testImplementation", libs.findLibrary("junit.jupiter.api").get())
                add("testImplementation", libs.findLibrary("junit.jupiter.params").get())
                add("testImplementation", libs.findLibrary("junit.jupiter.engine").get())
            }
        }
    }
}
