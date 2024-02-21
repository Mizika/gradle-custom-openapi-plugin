package custom.openapi.plugin.generatePlugin.utils

import java.io.File

class BuildGradleFromPlugin {

    fun buildGradle(pathToProject: String) {
        val fileName = "$pathToProject/build.gradle.example"
        if (!File(fileName).exists()) {
            val buildGradle = getResourceAsText("/buildGradle/buildGradle")!!
            File(fileName).createNewFile()
            File(fileName).writeText(buildGradle)
        }
    }

    private fun getResourceAsText(path: String): String? =
        object {}.javaClass.getResource(path)?.readText()
}