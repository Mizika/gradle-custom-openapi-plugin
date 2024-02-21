package custom.openapi.plugin.generatePlugin.utils

import java.io.File

class CreateTemplates {

    fun createTemplates(pathToProject: String) {
        createDirTemplates(pathToProject)
        createDirLibraries(pathToProject)
        createDirRestAssured(pathToProject)
        apiTemplate(pathToProject)
        modelTemplate(pathToProject)
        pojoTemplate(pathToProject)
    }

    private fun createDirTemplates(pathToProject: String) {
        val parentDirPath = "$pathToProject/src/main/resources"
        val dir = File(parentDirPath, "templates")
        if (!dir.exists()) {
            dir.mkdir()
        }
    }

    private fun createDirLibraries(pathToProject: String) {
        val parentDirPath = "$pathToProject/src/main/resources/templates"
        val dir = File(parentDirPath, "libraries")
        if (!dir.exists()) {
            dir.mkdir()
        }
    }

    private fun createDirRestAssured(pathToProject: String) {
        val parentDirPath = "$pathToProject/src/main/resources/templates/libraries"
        val dir = File(parentDirPath, "rest-assured")
        if (!dir.exists()) {
            dir.mkdir()
        }
    }

    private fun apiTemplate(pathToProject: String) {
        val fileName =
            "$pathToProject/src/main/resources/templates/libraries/rest-assured/api.mustache"
        if (!File(fileName).exists()) {
            val api = getResourceAsText("/templatesForGenerateModelAndClient/libraries/rest-assured/api.mustache")!!
            File(fileName).createNewFile()
            File(fileName).writeText(api)
        }
    }

    private fun modelTemplate(pathToProject: String) {
        val fileName = "$pathToProject/src/main/resources/templates/model.mustache"
        if (!File(fileName).exists()) {
            val api = getResourceAsText("/templatesForGenerateModelAndClient/model.mustache")!!
            File(fileName).createNewFile()
            File(fileName).writeText(api)
        }
    }

    private fun pojoTemplate(pathToProject: String) {
        val fileName = "$pathToProject/src/main/resources/templates/pojo.mustache"
        if (!File(fileName).exists()) {
            val api = getResourceAsText("/templatesForGenerateModelAndClient/pojo.mustache")!!
            File(fileName).createNewFile()
            File(fileName).writeText(api)
        }
    }

    private fun getResourceAsText(path: String): String? =
        object {}.javaClass.getResource(path)?.readText()
}

