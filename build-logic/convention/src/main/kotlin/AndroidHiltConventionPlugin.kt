import com.example.convention.utils.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("kotlin-kapt")
                apply("dagger.hilt.android.plugin")
            }

            dependencies {
                add("implementation", libs.findLibrary("hilt.android").get())
                add("kapt", libs.findLibrary("hilt.android.compiler").get())
                add("implementation", libs.findLibrary("hilt.navigation").get())

                // Test dependencies
                add("androidTestImplementation", libs.findLibrary("hilt.testing").get())
                add("kaptAndroidTest", libs.findLibrary("hilt.android.compiler").get())
                add("testImplementation", libs.findLibrary("hilt.testing").get())
                add("kaptTest", libs.findLibrary("hilt.android.compiler").get())
            }
        }
    }
}