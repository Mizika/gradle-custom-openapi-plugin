package custom.openapi.plugin.generatePlugin.utils

import java.io.File

class CreateClassForStarterSpring {

    fun createClassForStarterSpring(pathToProject: String) {
        createDirHelpers(pathToProject)
        createDirApp(pathToProject)
        createDirConfig(pathToProject)
        createDirModel(pathToProject)
        app(pathToProject)
        appConfig(pathToProject)
        testConfig(pathToProject)
        url(pathToProject)
        application(pathToProject)
        applicationDevelop(pathToProject)
        applicationTest(pathToProject)
    }

    private fun createDirHelpers(pathToProject: String) {
        val parentDirPath = "$pathToProject/src/main/java"
        val dir = File(parentDirPath, "helpers")
        if (!dir.exists()) {
            dir.mkdir()
        }
    }

    private fun createDirApp(pathToProject: String) {
        val parentDirPath = "$pathToProject/src/main/java"
        val dir = File(parentDirPath, "app")
        if (!dir.exists()) {
            dir.mkdir()
        }
    }

    private fun createDirConfig(pathToProject: String) {
        val parentDirPath = "$pathToProject/src/main/java/app"
        val dir = File(parentDirPath, "config")
        if (!dir.exists()) {
            dir.mkdir()
        }
    }

    private fun createDirModel(pathToProject: String) {
        val parentDirPath = "$pathToProject/src/main/java/app/config"
        val dir = File(parentDirPath, "model")
        if (!dir.exists()) {
            dir.mkdir()
        }
    }

    private fun app(pathToProject: String) {
        val fileName =
            "$pathToProject/src/main/java/app/App.java"
        if (!File(fileName).exists()) {
            val restAssuredHelper = getResourceAsText("/appClassForStarter/App")!!
            File(fileName).createNewFile()
            File(fileName).writeText(restAssuredHelper)
        }
    }

    private fun appConfig(pathToProject: String) {
        val fileName = "$pathToProject/src/main/java/app/config/AppConfig.java"
        if (!File(fileName).exists()) {
            val testBase = getResourceAsText("/appClassForStarter/AppConfig")!!
            File(fileName).createNewFile()
            File(fileName).writeText(testBase)
        }
    }

    private fun testConfig(pathToProject: String) {
        val fileName = "$pathToProject/src/main/java/app/config/TestConfig.java"
        if (!File(fileName).exists()) {
            val testConfig = getResourceAsText("/appClassForStarter/TestConfig")!!
            File(fileName).createNewFile()
            File(fileName).writeText(testConfig)
        }
    }

    private fun url(pathToProject: String) {
        val fileName = "$pathToProject/src/main/java/app/config/model/Url.java"
        if (!File(fileName).exists()) {
            val testExample = getResourceAsText("/appClassForStarter/UrlModel")!!
            File(fileName).createNewFile()
            File(fileName).writeText(testExample)
        }
    }

    private fun application(pathToProject: String) {
        val fileName = "$pathToProject/src/main/resources/application.yaml"
        if (!File(fileName).exists()) {
            val gitlabCi = getResourceAsText("/appClassForStarter/application")!!
            File(fileName).createNewFile()
            File(fileName).writeText(gitlabCi)
        }
    }

    private fun applicationDevelop(pathToProject: String) {
        val fileName = "$pathToProject/src/main/resources/application-develop.yaml"
        if (!File(fileName).exists()) {
            val gitlabCi = getResourceAsText("/appClassForStarter/application-develop")!!
            File(fileName).createNewFile()
            File(fileName).writeText(gitlabCi)
        }
    }

    private fun applicationTest(pathToProject: String) {
        val fileName = "$pathToProject/src/main/resources/application-test.yaml"
        if (!File(fileName).exists()) {
            val gitlabCi = getResourceAsText("/appClassForStarter/application-test")!!
            File(fileName).createNewFile()
            File(fileName).writeText(gitlabCi)
        }
    }


    private fun getResourceAsText(path: String): String? =
        object {}.javaClass.getResource(path)?.readText()
}

