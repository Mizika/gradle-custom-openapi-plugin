package custom.openapi.plugin.generatePlugin.utils

import io.swagger.v3.parser.OpenAPIV3Parser
import java.io.File


class DeleteApiTag {

    fun deleteMethodWithTagFromSwaggerSpec(spec: File, tag: String) {
        if (spec.readText().contains(tag)) {
            val openAPI = OpenAPIV3Parser().read(spec.path)
            val mapPathsForDelete = mutableSetOf<String>()
            openAPI.paths.keys.forEach { path ->
                if (openAPI.paths[path].toString().contains(tag)) {
                    mapPathsForDelete.add(path)
                }
            }
            mapPathsForDelete.forEach { deleteMethod -> openAPI.paths.remove(deleteMethod) }
            spec.writeText(
                io.swagger.v3.core.util.Yaml.mapper().writeValueAsString(openAPI)
            )
        }
    }
}


