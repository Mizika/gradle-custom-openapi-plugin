package custom.openapi.plugin.generatePlugin.utils

import java.io.File

class CreateUtilClassForGeneratedModelAndClient {

    fun createUtils(pathToProject: String) {
        createDirUtil(pathToProject)
        oper(pathToProject)
        jacksonObjectMapper(pathToProject)
        rFC3339DateFormat(pathToProject)
        responseSpecBuilders(pathToProject)
        allureFilter(pathToProject)
        clients(pathToProject)
    }

    private fun createDirUtil(pathToProject: String) {
        val parentDirPath = "$pathToProject/src/main/java/sw/generator/client"
        val dir = File(parentDirPath, "utils")
        if (!dir.exists()) {
            dir.mkdir()
        }
    }

    private fun rFC3339DateFormat(pathToProject: String) {
        val fileName = "$pathToProject/src/main/java/sw/generator/client/utils/RFC3339DateFormat.java"
        if (!File(fileName).exists()) {
            val rFC3339DateFormat = getResourceAsText("/utilClassForGenerateModelAndClient/RFC3339DateFormat")!!
            File(fileName).createNewFile()
            File(fileName).writeText(rFC3339DateFormat)
        }
    }

    private fun responseSpecBuilders(pathToProject: String) {
        val fileName =
            "$pathToProject/src/main/java/sw/generator/client/utils/ResponseSpecBuilders.java"
        if (!File(fileName).exists()) {
            val responseSpecBuilders = getResourceAsText("/utilClassForGenerateModelAndClient/ResponseSpecBuilders")!!
            File(fileName).createNewFile()
            File(fileName).writeText(responseSpecBuilders)
        }
    }

    private fun jacksonObjectMapper(pathToProject: String) {
        val fileName =
            "$pathToProject/src/main/java/sw/generator/client/utils/JacksonObjectMapper.java"
        if (!File(fileName).exists()) {
            val jacksonObjectMapper = getResourceAsText("/utilClassForGenerateModelAndClient/JacksonObjectMapper")!!
            File(fileName).createNewFile()
            File(fileName).writeText(jacksonObjectMapper)
        }
    }

    private fun oper(pathToProject: String) {
        val fileName = "$pathToProject/src/main/java/sw/generator/client/utils/Oper.java"
        if (!File(fileName).exists()) {
            val oper = getResourceAsText("/utilClassForGenerateModelAndClient/Oper")!!
            File(fileName).createNewFile()
            File(fileName).writeText(oper)
        }
    }

    private fun allureFilter(pathToProject: String) {
        val fileName = "$pathToProject/src/main/java/sw/generator/client/utils/AllureFilter.java"
        if (!File(fileName).exists()) {
            val allureFilter = getResourceAsText("/utilClassForGenerateModelAndClient/AllureFilter")!!
            File(fileName).createNewFile()
            File(fileName).writeText(allureFilter)
        }
    }

    private fun clients(pathToProject: String) {
        val fileName =
            "$pathToProject/src/main/java/sw/generator/client/utils/Clients.java"
        if (!File(fileName).exists()) {
            val clients = getResourceAsText("/utilClassForGenerateModelAndClient/Clients")!!
            File(fileName).createNewFile()
            File(fileName).writeText(clients)
        }
    }


    private fun getResourceAsText(path: String): String? =
        object {}.javaClass.getResource(path)?.readText()
}

