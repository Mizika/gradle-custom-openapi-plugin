package custom.openapi.plugin.generatePlugin

import custom.openapi.plugin.Extensions
import custom.openapi.plugin.generatePlugin.utils.BuildGradleFromPlugin
import custom.openapi.plugin.generatePlugin.utils.CreateClassForStarterSpring
import custom.openapi.plugin.generatePlugin.utils.CreateUtilClassForTest
import custom.openapi.plugin.generatePlugin.utils.generateTest.CreateTest

class ExecutionBeforeCreation {


    /**
     * Метод для создания:
     * build.gradle.example - пример билд грейдла
     * Utils классов для теста
     * Тестовых сценариев
     * @param extensions передаваймые расширения для такси generator
     */
    fun createBuildGradleAndUtilsClassForTest(extensions: Extensions, pathToProject: String) {
        if (extensions.utils) {
            BuildGradleFromPlugin().buildGradle(pathToProject)
            CreateUtilClassForTest().createUtilsClassesForTest(pathToProject)
            CreateClassForStarterSpring().createClassForStarterSpring(pathToProject)
            CreateTest().createTest(extensions, pathToProject)
        }
    }
}