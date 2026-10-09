package custom.openapi.plugin.generatePlugin.utils

import org.openapitools.generator.gradle.plugin.tasks.GenerateTask
import custom.openapi.plugin.Constants
import custom.openapi.plugin.Extensions
import java.io.File

class OpenApiTaskConfigurator {

    fun configure(
        task: GenerateTask,
        spec: File,
        pathToProject: String,
        extensions: Extensions
    ) {

        val nameFile = spec.name.substringBefore(".")

        task.generatorName.set("java")
        task.library.set("rest-assured")

        task.outputDir.set(File(pathToProject))

        task.generateModelDocumentation.set(false)
        task.generateApiDocumentation.set(false)

        task.configOptions.put(
            "dateLibrary",
            "java8"
        )

        task.configOptions.put(
            "serializationLibrary",
            "jackson"
        )

        task.configOptions.put(
            "useOneOfInterfaces",
            "true"
        )

        task.configOptions.put(
            "initialValues",
            "false"
        )

        task.typeMappings.put(
            "OffsetDateTime",
            "String"
        )

        task.globalProperties.put(
            "docs",
            "false"
        )

        task.globalProperties.put(
            "apis",
            ""
        )

        task.globalProperties.put(
            "models",
            ""
        )

        task.skipValidateSpec.set(true)

        task.groupId.set(
            "custom.generator"
        )

        task.id.set(
            "client"
        )

        task.generateModelTests.set(false)
        task.generateApiTests.set(false)


        task.invokerPackage.set(
            Constants.GeneratorPlugin.BASE_PACKAGE
        )

        task.inputSpec.set(spec)

        task.apiPackage.set(
            "${Constants.GeneratorPlugin.BASE_PACKAGE}.${nameFile.lowercase()}.api"
        )


        task.modelPackage.set(
            "${Constants.GeneratorPlugin.BASE_PACKAGE}.${nameFile.lowercase()}.model"
        )


        task.additionalProperties.put(
            "productName",
            nameFile.replaceFirstChar {
                it.uppercase()
            }
        )


        task.templateDir.set(
            File(extensions.templateDir)
        )


        task.openapiNormalizer.put(
            "REF_AS_PARENT_IN_ALLOF",
            "true"
        )
    }
}