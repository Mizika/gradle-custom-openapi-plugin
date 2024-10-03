package custom.openapi.plugin.generatePlugin.utils

import io.swagger.v3.parser.OpenAPIV3Parser
import java.io.File


class DeleteDeprecatedMethod {

    fun deleteDeprecatedMethod(spec: File) {
        val openAPI = OpenAPIV3Parser().read(spec.path)
        val mapPathsForDelete = mutableSetOf<String>()
        openAPI.paths.keys.forEach { method ->
            openAPI.paths[method]?.readOperationsMap()?.forEach { actionEach ->
                if (actionEach.value.deprecated != null) {
                    mapPathsForDelete.add(method)
                }
            }
        }
        mapPathsForDelete.forEach { deleteMethod -> openAPI.paths.remove(deleteMethod) }
        spec.writeText(
            io.swagger.v3.core.util.Yaml.mapper().writeValueAsString(openAPI)
        )
    }
}


