package custom.openapi.plugin.generatePlugin.utils

import io.swagger.v3.parser.OpenAPIV3Parser
import java.io.File


/**
 * Метод для удаления параметра writeOnly
 */
class DeleteWriteOnlyParam {

    fun deleteWriteOnlyParam(spec: File) {
        val openAPI = OpenAPIV3Parser().read(spec.path)
        println("====Start delete writeOnly in property====")
        openAPI.components?.schemas?.forEach { (modelName, schema) ->
            // Создаем копию свойств, чтобы избежать ConcurrentModificationException при удалении
            val propertiesCopy = schema.properties?.toMutableMap()
            propertiesCopy?.forEach { (propertyName, propertySchema) ->
                // Проверяем, установлен ли атрибут writeOnly
                if (propertySchema.writeOnly != null) {
                    propertySchema.writeOnly = null
                    println("Removed writeOnly attribute for property \"$propertyName\" in model \"$modelName\"")
                }
            }
            // Также нужно проверить свойства внутри allOf
            schema.allOf?.forEach { subschema ->
                subschema?.properties?.toMutableMap()?.forEach { (propertyName, propertySchema) ->
                    if (propertySchema.writeOnly != null) {
                        propertySchema.writeOnly = null
                        println("Removed writeOnly attribute for property \"$propertyName\" in allOf model \"$modelName\"")
                    }
                }
            }
        }
        spec.writeText(
            io.swagger.v3.core.util.Yaml.mapper().writeValueAsString(openAPI)
        )
        println("====End delete writeOnly====")
    }

}


