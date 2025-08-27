package custom.openapi.plugin.generatePlugin.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import io.swagger.v3.parser.OpenAPIV3Parser
import java.io.File

/**
 * Метод для удаления параметра readOnly
 */
class DeleteReadOnlyParam {

    fun deleteReadOnlyParam(spec: File) {
        println("====Start delete readOnly in property (text mode)====")
        val cleanedLines = spec.readLines().filterNot { line ->
            line.trimStart().startsWith("readOnly:")
        }
        spec.writeText(cleanedLines.joinToString("\n") + "\n")
        println("====End delete readOnly====")
    }
}


