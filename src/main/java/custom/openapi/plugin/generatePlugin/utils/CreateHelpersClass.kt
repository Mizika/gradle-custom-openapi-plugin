package custom.openapi.plugin.generatePlugin.utils

import io.swagger.v3.parser.OpenAPIV3Parser
import java.io.File

class CreateHelpersClass {

    fun createHelpers(spec: File, nameFile: String, pathToProject: String, pathToFolder: String) {
        val mapPathsToAddHelper = getOperationIdFromSwagger(spec = spec)
        createHelperClass(
            nameFile = nameFile,
            pathToProject = pathToProject,
            pathToFolder = pathToFolder
        )
        createMethodInHelper(
            mapPathsToAddHelper = mapPathsToAddHelper,
            nameFile = nameFile,
            pathToProject = pathToProject,
            pathToFolder = pathToFolder
        )
    }


    private fun createMethodInHelper(
        mapPathsToAddHelper: MutableSet<String>,
        nameFile: String,
        pathToProject: String,
        pathToFolder: String
    ) {
        val helperName = "${nameFile.capitalize()}Helper"
        val fileName = "$pathToProject/$pathToFolder/${helperName}.java"
        mapPathsToAddHelper.forEach { method ->
            val methodFormat = "\n" +
                    "    /**\n" +
                    "     * Метод для ...\n" +
                    "     */\n" +
                    "    public void $method {}\n"
            insertMethodToHelper(fileName, method, methodFormat)
        }

    }

    private fun createHelperClass(nameFile: String, pathToProject: String, pathToFolder: String) {
        val helperName = "${nameFile.capitalize()}Helper"
        val fileName = "$pathToProject/$pathToFolder/${helperName}.java"
        if (!File(fileName).exists()) {
            File(fileName).createNewFile()
            File(fileName).writeText(
                "package helpers;\n" +
                        "\n" +
                        "import org.springframework.beans.factory.annotation.Autowired;\n" +
                        "import org.springframework.stereotype.Component;\n" +
                        "\n" +
                        "@Component\n" +
                        "public class $helperName {}"
            )
        }
    }

    private fun getOperationIdFromSwagger(spec: File): MutableSet<String> {
        val openAPI = OpenAPIV3Parser().read(spec.path)
        val mapPathsToAddHelper = mutableSetOf<String>()
        openAPI.paths.keys.forEach { path ->
            if (openAPI.paths[path]?.get != null && openAPI.paths[path]?.get?.operationId != null) {
                mapPathsToAddHelper.add("${openAPI.paths[path]?.get?.operationId.toString()}()")
            } else if (openAPI.paths[path]?.post != null && openAPI.paths[path]?.post?.operationId != null) {
                mapPathsToAddHelper.add("${openAPI.paths[path]?.post?.operationId.toString()}()")
            } else if (openAPI.paths[path]?.patch != null && openAPI.paths[path]?.patch?.operationId != null) {
                mapPathsToAddHelper.add("${openAPI.paths[path]?.patch?.operationId.toString()}()")
            } else if (openAPI.paths[path]?.delete != null && openAPI.paths[path]?.delete?.operationId != null) {
                mapPathsToAddHelper.add("${openAPI.paths[path]?.delete?.operationId.toString()}()")
            } else if (openAPI.paths[path]?.put != null && openAPI.paths[path]?.put?.operationId != null) {
                mapPathsToAddHelper.add("${openAPI.paths[path]?.put?.operationId.toString()}()")
            } else if (openAPI.paths[path]?.trace != null && openAPI.paths[path]?.trace?.operationId != null) {
                mapPathsToAddHelper.add("${openAPI.paths[path]?.trace?.operationId.toString()}()")
            }
        }
        return mapPathsToAddHelper
    }

    private fun insertMethodToHelper(helperPath: String, method: String, addMethod: String) {
        val helperFile = File(helperPath)
        if (!helperFile.readText().contains(method)) {
            val lastCharacter = helperFile.readText().lastIndexOf("}")
            helperFile.writeText(StringBuilder(helperFile.readText()).deleteAt(lastCharacter).toString())
            helperFile.writeText(helperFile.readText().trim() + "\n$addMethod")
            helperFile.writeText(helperFile.readText().trim() + "\n}")
        }
    }

}