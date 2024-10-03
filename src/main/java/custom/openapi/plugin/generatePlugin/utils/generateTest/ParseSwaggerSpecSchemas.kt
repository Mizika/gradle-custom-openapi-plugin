package custom.openapi.plugin.generatePlugin.utils.generateTest

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.parser.OpenAPIV3Parser
import java.io.File

class ParseSwaggerSpecSchemas {

    /**
     * Поиск обязательный полей в моделях (components)
     * @return MutableMap<String, MutableList<String>> имя модели и  лист с именами полей в модели
     */
    //TODO: если в спеке указан allOf не собирается боди запроса. Исправить!
    fun searchRequiredFieldInBody(openAPI: OpenAPI): MutableMap<String, MutableList<String>> {
        val mapWithSchema: MutableMap<String, MutableList<String>> = mutableMapOf()
        openAPI.components.schemas.forEach { schema ->
            if (schema.value.required != null) {
                mapWithSchema[schema.key] = schema.value.required
            }
        }
        return mapWithSchema
    }

    //TODO: В данном методе реализована часть по сбору всех данных для реквеста из моделей, необходимо доделать.
    fun getAllDataFromSchema() {
        File("spec2").walkTopDown().maxDepth(1)
            .filter { file ->
                file.isFile && file.name.contains(Regex("^.*.yaml$|^.*.yml$"))
            }.forEach { fileSpec ->
                val mapEnum = mutableMapOf<String, List<Any>>()
                val mapElement: MutableMap<String, MutableMap<String, String>> = mutableMapOf()
                val openAPI = OpenAPIV3Parser().read(fileSpec.path)
                openAPI.components.schemas.forEach { schema ->
                    schema.value.enum.takeIf { r -> r != null }?.apply {
                        mapEnum[schema.key] = schema.value.enum
                    }
                }
                openAPI.components.schemas.forEach { schema ->
                    val mapFields = mutableMapOf<String, String>()
                    schema.value.type.takeIf { r -> r.equals("object") }?.apply {
                        println(schema.key)
                        println(schema.value.required)
                        println("====================")
                        schema.value.properties.forEach { fields ->
                            if (fields.value.`$ref` != null) {
                                val nameEnum = fields.value.`$ref`.substringAfterLast("/")
                                mapFields[fields.key] = mapEnum[nameEnum].toString()
                            } else {
                                mapFields[fields.key] = fields.value.type
                            }
                        }
                    }
                    mapElement[schema.key] = mapFields
                }
                println("=================")
                println(fileSpec)
                println(mapElement)
            }
    }
}