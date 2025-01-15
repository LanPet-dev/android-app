import com.example.convention.utils.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.buildscript
import org.gradle.kotlin.dsl.dependencies

class AndroidJunit5ConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("convention.android.test.junit5")
            }

            dependencies {
                add("testImplementation", libs.findLibrary("junit.jupiter.api").get())
                add("testImplementation", libs.findLibrary("junit.jupiter.params").get())
                add("testRuntimeOnly", libs.findLibrary("junit.jupiter.engine").get())
                add("testImplementation", libs.findLibrary("junit.jupiter").get())
                add("testImplementation", libs.findLibrary("mockk.android").get())
                add("testImplementation", libs.findLibrary("turbine").get())
                add("testImplementation", project(":core:testing"))
                add("testImplementation", libs.findLibrary("kotlinx.coroutines.test").get())
            }
        }
    }
}
