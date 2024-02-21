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
        val basePackage = Constants.GeneratorPlugin.BASE_PACKAGE
        val pathToApiClient = pathToProject +
                "/src/main/java/${basePackage.replace(".", "/")}/${nameFile.lowercase(Locale.getDefault())}/api"
        val pathToClientHelper = pathToProject + "/src/main/java/${
            basePackage.replace(
                ".",
                "/"
            )
        }/${nameFile.lowercase(Locale.getDefault())}/${nameFile}CallingGeneratedClients"
        RenameGenerateFiles().renameApiClient(pathToApiClient)
        CreateUtilClassForGeneratedModelAndClient().createUtils(pathToProject)
        if (extensions.testConfig.isNotBlank()) {
            CreateBeanInTestConfig().createBeanInTestConfig(
                pathToApiClient,
                pathToProject + extensions.testConfig
            )
        } else {
            CreateBeanInTestConfig().createBeanHelper(pathToApiClient, pathToClientHelper)
        }
        if (extensions.helpers.isNotBlank()) {
            CreateHelpersClass().createHelpers(
                spec = spec,
                nameFile = nameFile,
                pathToProject = pathToProject,
                pathToFolder = extensions.helpers
            )
        }
    }
}