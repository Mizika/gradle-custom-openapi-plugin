package custom.openapi.plugin.generatePlugin.utils

import io.swagger.v3.parser.OpenAPIV3Parser
import java.io.File
import io.swagger.v3.core.util.Yaml

class FixXImplementsDto {

    fun removeDtoSuffix(spec: File) {
        val openAPI = OpenAPIV3Parser().read(spec.path)
        println("==== Start fixing x-implements Dto suffix ====")

        var modified = false

        openAPI.components?.schemas?.forEach { (modelName, schema) ->
            val extensions = schema.extensions ?: return@forEach
            val original = extensions["x-implements"] as? List<*> ?: return@forEach

            val updated = original.mapNotNull { entry ->
                (entry as? String)?.let { name ->
                    if (name.endsWith("Dto")) {
                        val newName = name.removeSuffix("Dto")
                        println("Updated in $modelName: $name -> $newName")
                        modified = true
                        newName
                    } else name
                }
            }

            if (updated != original) {
                extensions["x-implements"] = updated
                schema.extensions = extensions
            }
        }

        if (modified) {
            spec.writeText(Yaml.mapper().writeValueAsString(openAPI))
            println("==== Saved updated spec ====")
        } else {
            println("==== No x-implements with Dto suffix found ====")
        }
    }
}
