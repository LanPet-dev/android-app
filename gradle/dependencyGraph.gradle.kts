tasks.register("projectDependencyGraph") {
    doLast {
        val dot = buildDir.resolve("reports/dependency-graph/project.dot")
        dot.parentFile.mkdirs()

        dot.writeText(buildString {
            appendLine("digraph {")
            appendLine("  graph [rankdir=TB]")
            appendLine("  node [shape=box]")

            // 모든 프로젝트를 노드로 추가
            allprojects.forEach { project ->
                val color = if (project == rootProject) "orange" else "skyblue"
                appendLine("  \"${project.path}\" [style=filled, fillcolor=$color]")
            }

            // 프로젝트 간의 의존성을 엣지로 추가
            allprojects.forEach { project ->
                project.configurations
                        .filter { it.isCanBeResolved }
                        .forEach { configuration ->
                            configuration.dependencies
                                    .filterIsInstance<org.gradle.api.artifacts.ProjectDependency>()
                                    .forEach { dependency ->
                                        appendLine("  \"${project.path}\" -> \"${dependency.dependencyProject.path}\"")
                                    }
                        }
            }

            appendLine("}")
        })

        exec {
            commandLine("dot", "-Tpng", "-O", dot.absolutePath)
        }
    }
}