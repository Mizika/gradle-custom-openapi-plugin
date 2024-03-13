package custom.openapi.plugin.generatePlugin

import custom.openapi.plugin.Constants
import custom.openapi.plugin.Extensions
import custom.openapi.plugin.generatePlugin.utils.CreateBeanInTestConfig
import custom.openapi.plugin.generatePlugin.utils.CreateHelpersClass
import custom.openapi.plugin.generatePlugin.utils.CreateUtilClassForGeneratedModelAndClient
import custom.openapi.plugin.generatePlugin.utils.RenameGenerateFiles
import java.io.File
import java.util.*

class DoLastInCreationTask {

    /**
     * Метод, что происходит внутри таски после генерации клиентов и моделей:
     *
     * <i>RenameGenerateFiles().renameApiClient(pathToApiClient)</i> - задаем нормальное имя для клиентов;
     *
     * <i>CreateUtilClassForGeneratedModelAndClient().createUtils()</i> - создание папки utils с utils Классами для клиентов, моделей и тестов;
     *
     * <i>CreateBeanInTestConfig().createBeanInTestConfig()</i> - создание бина в указоном файле после генерации если файл не указан
     * выполнфется метод
     *
     * CreateBeanInTestConfig().createBeanHelper() - создания файла с примеров вызова сгенерированного клиента
     *
     * @param spec файл спецификации
     * @param extensions передаваймые расширения для такси generator
     * @param pathToProject путь до проекта
     */
    fun doLastInCreationTask(spec: File, extensions: Extensions, pathToProject: String) {
        val nameFile = spec.name.substringBefore(".")
        val nameSpecUpper = nameFile.uppercase(Locale.getDefault())
        val nameSpecLower = nameFile.lowercase(Locale.getDefault())
        val basePackage = Constants.GeneratorPlugin.BASE_PACKAGE
        val pathToApiClient = pathToProject +
                "/src/main/java/${basePackage.replace(".", "/")}/${nameSpecLower}/api"
        val pathToClientHelper = pathToProject + "/src/main/java/${
            basePackage.replace(
                ".",
                "/"
            )
        }/${nameSpecLower}/${nameFile}CallingGeneratedClients"
        println("===============")
        println("Rename api client for \"${nameSpecUpper}\" path \"${pathToApiClient}\"")
        RenameGenerateFiles().renameApiClient(pathToApiClient)
        println("Rename api client end success!")
        println("===============")
        if (extensions.utils) {
            println("Create utils class \"${nameSpecUpper}\" path \"${pathToProject}\"")
            CreateUtilClassForGeneratedModelAndClient().createUtils(pathToProject)
            println("Create utils class end success!")
        }
        if (extensions.testConfig.isNotBlank()) {
            println("===============")
            println("Create bean for \"${nameSpecUpper}\" path \"${pathToProject + extensions.testConfig}\"")
            CreateBeanInTestConfig().createBeanInTestConfig(
                pathToApiClient,
                pathToProject + extensions.testConfig
            )
            println("Create bean end success!")
        } else {
            println("===============")
            println("Create helpers to call client for \"${nameSpecUpper}\" path: \"${pathToClientHelper}\"")
            CreateBeanInTestConfig().createBeanHelper(pathToApiClient, pathToClientHelper)
            println("Create helpers to call client end success!")
        }
        if (extensions.helpers.isNotBlank()) {
            println("===============")
            println("Create class helper with call methods for \"${nameSpecUpper}\"")
            CreateHelpersClass().createHelpers(
                spec = spec,
                nameFile = nameFile,
                pathToProject = pathToProject,
                pathToFolder = extensions.helpers
            )
            println("Create class helper with call methods end success!")
        }
    }
}