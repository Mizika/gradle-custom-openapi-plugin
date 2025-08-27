package custom.openapi.plugin.generatePlugin.utils

import io.swagger.v3.parser.OpenAPIV3Parser
import java.io.File
import io.swagger.v3.core.util.Yaml

class FixXImplementsDto {

    fun removeDtoSuffix(spec: File) {
        println("==== Start fixing x-implements Dto suffix (text mode) ====")

        val lines = spec.readLines().toMutableList()
        val result = mutableListOf<String>()
        var modified = false

        for (line in lines) {
            if (line.trimStart().startsWith("- ") && line.contains("Dto")) {
                val updatedLine = line.replace(Regex("(\\w+)Dto")) {
                    val original = it.value
                    val newName = original.removeSuffix("Dto")
                    println("Updated: $original -> $newName")
                    modified = true
                    newName
                }
                result.add(updatedLine)
            } else {
                result.add(line)
            }
        }

        if (modified) {
            spec.writeText(result.joinToString("\n") + "\n")
            println("==== Saved updated spec ====")
        } else {
            println("==== No x-implements with Dto suffix found ====")
        }
    }
}
