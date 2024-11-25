import com.example.convention.utils.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidCoilLibConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                add("implementation", libs.findLibrary("coil.compose").get())
                add("implementation", libs.findLibrary("coil.network.okhttp").get())
                add("implementation", libs.findLibrary("coil.svg").get())
            }
        }
    }
}