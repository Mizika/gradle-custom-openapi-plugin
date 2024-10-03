package custom.openapi.plugin.generatePlugin.utils

import java.io.File

class CreateUtilClassForTest {

    fun createUtilsClassesForTest(pathToProject: String) {
        createDirRu(pathToProject)
        createDirPost(pathToProject)
        createDirTestCases(pathToProject)
        createDirUtil(pathToProject)
        restAssuredHelper(pathToProject)
        testBase(pathToProject)
//        testExample(pathToProject)
        gitlabCi()
        gitignore()
        allureProperties(pathToProject)
        allureGradle(pathToProject)
    }

    private fun createDirRu(pathToProject: String) {
        val parentDirPath = "$pathToProject/src/test/java"
        val dir = File(parentDirPath, "ru")
        if (!dir.exists()) {
            dir.mkdir()
        }
    }

    private fun createDirPost(pathToProject: String) {
        val parentDirPath = "$pathToProject/src/test/java/ru"
        val dir = File(parentDirPath, "russianpost")
        if (!dir.exists()) {
            dir.mkdir()
        }
    }

    private fun createDirTestCases(pathToProject: String) {
        val parentDirPath = "$pathToProject/src/test/java/ru/russianpost"
        val dir = File(parentDirPath, "testcases")
        if (!dir.exists()) {
            dir.mkdir()
        }
    }

    private fun createDirUtil(pathToProject: String) {
        val parentDirPath = "$pathToProject/src/test/java/ru/russianpost"
        val dir = File(parentDirPath, "utils")
        if (!dir.exists()) {
            dir.mkdir()
        }
    }

    private fun restAssuredHelper(pathToProject: String) {
        val fileName =
            "$pathToProject/src/test/java/ru/russianpost/utils/RestAssuredHelper.java"
        if (!File(fileName).exists()) {
            val restAssuredHelper = getResourceAsText("/utilClassForTest/RestAssuredHelper")!!
            File(fileName).createNewFile()
            File(fileName).writeText(restAssuredHelper)
        }
    }

    private fun testBase(pathToProject: String) {
        val fileName = "$pathToProject/src/test/java/ru/russianpost/utils/TestBase.java"
        if (!File(fileName).exists()) {
            val testBase = getResourceAsText("/utilClassForTest/TestBase")!!
            File(fileName).createNewFile()
            File(fileName).writeText(testBase)
        }
    }

    private fun testExample(pathToProject: String) {
        val fileName = "$pathToProject/src/test/java/ru/russianpost/testcases/ExampleTest.java"
        if (!File(fileName).exists()) {
            val testExample = getResourceAsText("/utilClassForTest/ExampleTest")!!
            File(fileName).createNewFile()
            File(fileName).writeText(testExample)
        }
    }

    private fun gitlabCi() {
        val fileName = "${System.getProperty("user.dir")}/.gitlab-ci.yml"
        if (!File(fileName).exists()) {
            val gitlabCi = getResourceAsText("/utilClassForTest/gitlab-ci")!!
            File(fileName).createNewFile()
            File(fileName).writeText(gitlabCi)
        }
    }

    private fun gitignore() {
        val fileName = "${System.getProperty("user.dir")}/.gitignore"
        if (!File(fileName).exists()) {
            val gitlabCi = getResourceAsText("/utilClassForTest/gitignore")!!
            File(fileName).createNewFile()
            File(fileName).writeText(gitlabCi)
        }
    }

    private fun allureProperties(pathToProject: String) {
        val fileName = "$pathToProject/src/test/resources/allure.properties"
        if (!File(fileName).exists()) {
            val gitlabCi = getResourceAsText("/utilClassForTest/allureProperties")!!
            File(fileName).createNewFile()
            File(fileName).writeText(gitlabCi)
        }
    }

    private fun allureGradle(pathToProject: String) {
        val fileName = "$pathToProject/allure.gradle"
        if (!File(fileName).exists()) {
            val gitlabCi = getResourceAsText("/utilClassForTest/allureGradle")!!
            File(fileName).createNewFile()
            File(fileName).writeText(gitlabCi)
        }
    }


    private fun getResourceAsText(path: String): String? =
        object {}.javaClass.getResource(path)?.readText()
}

