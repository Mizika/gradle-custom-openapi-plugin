package custom.openapi.plugin.generatePlugin.utils

import io.swagger.v3.parser.OpenAPIV3Parser
import java.io.File

/**
 * Метод для удаления параметра readOnly
 */
class DeleteReadOnlyParam {

    fun deleteReadOnlyParam(spec: File) {
        val openAPI = OpenAPIV3Parser().read(spec.path)
        println("====Start delete readOnly in property====")
        openAPI.components?.schemas?.forEach { (modelName, schema) ->
            // Создаем копию свойств, чтобы избежать ConcurrentModificationException при удалении
            val propertiesCopy = schema.properties?.toMutableMap()
            propertiesCopy?.forEach { (propertyName, propertySchema) ->
                // Проверяем, установлен ли атрибут readOnly
                if (propertySchema.readOnly != null) {
                    propertySchema.readOnly = null
                    println("Removed readOnly attribute for property \"$propertyName\" in model \"$modelName\"")
                }
            }
            // Проверяем свойства внутри allOf
            schema.allOf?.forEach { subschema ->
                subschema?.properties?.toMutableMap()?.forEach { (propertyName, propertySchema) ->
                    if (propertySchema.readOnly != null) {
                        propertySchema.readOnly = null
                        println("Removed readOnly attribute for property \"$propertyName\" in allOf model \"$modelName\"")
                    }
                }
            }
        }
        spec.writeText(
            io.swagger.v3.core.util.Yaml.mapper().writeValueAsString(openAPI)
        )
        println("====End delete readOnly====")
    }
}


