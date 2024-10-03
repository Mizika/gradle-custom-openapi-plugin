package custom.openapi.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import custom.openapi.plugin.generatePlugin.DoFirstInCreationTask
import custom.openapi.plugin.generatePlugin.DoLastInCreationTask
import custom.openapi.plugin.generatePlugin.ExecutionBeforeCreation
import custom.openapi.plugin.generatePlugin.utils.Download
import custom.openapi.plugin.generatePlugin.utils.FindSpecificationFiles
import custom.openapi.plugin.generatePlugin.utils.OpenApiConfig

class Plugin : Plugin<Project> {

    override fun apply(project: Project) {
        val extensions: Extensions = project.extensions.create("generator", Extensions::class.java)

        Dependencies().addDependenciesToProject(project)

        project.afterEvaluate {
            val pathToProject = project.projectDir.absolutePath
            Download().downloadAndSaveSpecification(extensions = extensions, pathToProject)
            ExecutionBeforeCreation().createBuildGradleAndUtilsClassForTest(extensions = extensions, pathToProject)
            FindSpecificationFiles().findSpec(pathToSpec = extensions.specToGenerate, pathToProject = pathToProject)
                .forEach { spec ->
                    project.tasks.register(
                        "generate${spec.name.substringBefore(".").capitalize()}",
                        OpenApiConfig::class.java
                    ) { task ->
                        task.parameters(
                            spec = spec,
                            basePackage = Constants.GeneratorPlugin.BASE_PACKAGE,
                            pathToProject,
                            extensions
                        )
                        task.doFirst {
                            DoFirstInCreationTask().doFirstInCreationTask(
                                spec = spec,
                                pathToProject = pathToProject,
                                extensions = extensions
                            )
                        }
                        task.doLast {
                            DoLastInCreationTask().doLastInCreationTask(
                                spec = spec,
                                extensions = extensions,
                                pathToProject
                            )
                        }
                        task.group = "openapi plugin"
                    }
                }
        }
    }
}