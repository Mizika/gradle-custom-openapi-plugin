package custom.openapi.plugin.generatePlugin.utils

import io.swagger.v3.parser.OpenAPIV3Parser
import java.io.File


/**
 * Метод для удаления параметра writeOnly
 */
class DeleteWriteOnlyParam {

    fun deleteWriteOnlyParam(spec: File) {
        println("====Start delete writeOnly in property (text mode)====")
        val cleanedLines = spec.readLines().filterNot { line ->
            line.trimStart().startsWith("writeOnly:")
        }
        spec.writeText(cleanedLines.joinToString("\n") + "\n")
        println("====End delete writeOnly====")
    }

}


