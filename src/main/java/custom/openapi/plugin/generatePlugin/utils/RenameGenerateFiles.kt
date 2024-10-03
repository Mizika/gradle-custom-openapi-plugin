package custom.openapi.plugin.generatePlugin.utils

import java.io.File

class RenameGenerateFiles {

    fun renameApiClient(pathToClient: String) {
        File(pathToClient)
            .walk(FileWalkDirection.BOTTOM_UP).filter { f -> f.name.contains(Regex(".*.java")) }.forEach { apiFile ->
                apiFile.writeText(apiFile.readText().replace("ProductApiApi", "ProductApi"))
                var newFileName = ""
                apiFile.forEachLine {
                    if (it.contains(Regex("public class .*"))) {
                        newFileName = it.replace("public class ", "")
                            .replace(" {", "")
                    }
                }
                apiFile.renameTo(File("${apiFile.parent}/$newFileName.java"))
            }
    }
}