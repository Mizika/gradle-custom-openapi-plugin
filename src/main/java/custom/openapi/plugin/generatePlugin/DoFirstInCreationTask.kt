package custom.openapi.plugin.generatePlugin

import custom.openapi.plugin.Constants
import custom.openapi.plugin.Extensions
import custom.openapi.plugin.generatePlugin.utils.CreateTemplates
import custom.openapi.plugin.generatePlugin.utils.DeleteApiTag
import custom.openapi.plugin.generatePlugin.utils.DeleteDeprecatedMethod
import java.io.File
import java.util.*

class DoFirstInCreationTask {


    /**
     * Метод, что происходит внутри таски первое:
     *
     * <i>CreateTemplates().createTemplates()</i> - создание темплейтов для генерации клиентов и моделей;
     *
     * <i>File(pathToApiClient).deleteRecursively()</i> - удаление всех файлов из папки api для генерируемой спецификации (если существовала ранее);
     *
     * <i>File(pathToModel).deleteRecursively()</i> - удаление всех файлов из папки model для генерируемой спецификации (если существовала ранее);
     *
     * @param spec файл спецификации
     * @param pathToProject путь до проекта
     */
    fun doFirstInCreationTask(spec: File, pathToProject: String, extensions: Extensions) {
        if (extensions.deleteRabbitMq) {
            DeleteApiTag().deleteMethodWithTagFromSwaggerSpec(
                spec = spec,
                tag = "RabbitMQ"
            )
        }
        if (extensions.deleteDeprecatedMethod) {
            DeleteDeprecatedMethod().deleteDeprecatedMethod(spec = spec)
        }
        val nameFile = spec.name.substringBefore(".")
        val basePackage = Constants.GeneratorPlugin.BASE_PACKAGE
        val pathToApiClient = pathToProject +
                "/src/main/java/${basePackage.replace(".", "/")}/${nameFile.lowercase(Locale.getDefault())}/api"
        val pathToModel = pathToProject +
                "/src/main/java/${basePackage.replace(".", "/")}/${nameFile.lowercase(Locale.getDefault())}/model"

        if (extensions.utils) {
            CreateTemplates().createTemplates(pathToProject)
        }
        File(pathToApiClient).deleteRecursively()
        File(pathToModel).deleteRecursively()
        println("===============")
        println("Start generate client and models for \"${nameFile.uppercase(Locale.getDefault())}\"")
        println("===============")
    }
}