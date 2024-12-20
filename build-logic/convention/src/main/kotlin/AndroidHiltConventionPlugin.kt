import com.example.convention.utils.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.accessors.runtime.addDependencyTo
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.support.kotlinCompilerOptions

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
                apply("dagger.hilt.android.plugin")
            }

            dependencies {
                add("implementation", libs.findLibrary("hilt.android").get())
                add("ksp", libs.findLibrary("hilt.android.compiler").get())
                add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())

                // Test dependencies
                add("androidTestImplementation", libs.findLibrary("hilt.testing").get())
                add("kspAndroidTest", libs.findLibrary("hilt.android.compiler").get())
                add("testImplementation", libs.findLibrary("hilt.testing").get())
                add("kspTest", libs.findLibrary("hilt.android.compiler").get())
            }
        }
    }
}