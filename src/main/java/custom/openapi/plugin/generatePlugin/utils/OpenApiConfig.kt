package custom.openapi.plugin.generatePlugin.utils

import org.gradle.api.model.ObjectFactory
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask
import custom.openapi.plugin.Extensions
import java.io.File
import java.util.*
import javax.inject.Inject

open class OpenApiConfig @Inject constructor(
    private val objectFactory: ObjectFactory
) : GenerateTask(objectFactory) {

    fun parameters(spec: File, basePackage: String, pathToProject: String, extensions: Extensions) {
        val nameFile = spec.name.substringBefore(".")
        generatorName.set("java")
        library.set("rest-assured")
        outputDir.set(pathToProject)
        generateModelDocumentation.set(false)
        generateApiDocumentation.set(false)
        configOptions.put("dateLibrary", "java8")
        configOptions.put("serializationLibrary", "jackson")
        configOptions.put("useOneOfInterfaces", "true")
        configOptions.put("hideGenerationTimestamp", "true") // это отключает генерацию даты в аннотаци @javax.annotation.Generated в моделях
        typeMappings.put("OffsetDateTime", "String")
        globalProperties.put("docs", "false")
        globalProperties.put("apis", "")
        globalProperties.put("models", "")
        globalProperties.put("openApiNullable", "true")
        skipValidateSpec.set(true)
        groupId.set("custom.generator")
        id.set("client")
        generateModelTests.set(false)
        generateApiTests.set(false)
        invokerPackage.set(basePackage)
        inputSpec.set(spec.path)
        apiPackage.set("${basePackage}.${nameFile.lowercase(Locale.getDefault())}.api")
        modelPackage.set("${basePackage}.${nameFile.lowercase(Locale.getDefault())}.model")
        additionalProperties.put("productName", nameFile.lowercase(Locale.getDefault()).capitalize())
        templateDir.set(extensions.templateDir)
        openapiNormalizer.put("REF_AS_PARENT_IN_ALLOF", "true")
    }

}