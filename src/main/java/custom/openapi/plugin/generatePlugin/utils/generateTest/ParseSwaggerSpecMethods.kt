package custom.openapi.plugin.generatePlugin.utils.generateTest

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.PathItem
import java.util.*


class ParseSwaggerSpecMethods {

    /**
     * Поиск value-parameter в сваггер документации
     * @return Map.Entry<PathItem.HttpMethod, Operation> - все необходимые данные по методам
     */
    fun action(openAPI: OpenAPI): Map.Entry<PathItem.HttpMethod, Operation> {
        var actionFind: Map.Entry<PathItem.HttpMethod, Operation> =
            AbstractMap.SimpleEntry<PathItem.HttpMethod, Operation>(null, null)
        openAPI.paths.keys.forEach { method ->
            openAPI.paths[method]?.readOperationsMap()?.forEach { actionEach ->
                actionEach.value.takeIf { r -> r.deprecated == null && (r.tags == null || !r.tags.contains("RabbitMQ")) }
                    ?.apply {
                        actionFind = actionEach
                    }
            }
        }
        return actionFind
    }

    /**
     * Поиск имени модели запроса Request Model
     * @return String - имя модели
     */
    fun requestModelName(action: Map.Entry<PathItem.HttpMethod, Operation>): String {
        var requestModelName = ""
        action.value.takeIf { r -> r.requestBody != null }?.apply {
            action.value.requestBody.content.values.forEach {
                if (it.schema.`$ref` != null) {
                    requestModelName = it.schema.`$ref`.substringAfterLast("/")
                }
            }
        }
        return requestModelName
    }

    /**
     * Поиск имени модели ответа Response Model
     * @return String - имя модели
     */
    fun responsesModelName(action: Map.Entry<PathItem.HttpMethod, Operation>): String {
        var responsesModelName = ""
        action.value.takeIf { r -> r.responses != null }?.apply {
            action.value.responses.get("200")?.content?.values?.forEach { schema ->
                schema.schema.takeIf { r -> r.`$ref` != null }?.apply {
                    responsesModelName = schema.schema.`$ref`.substringAfterLast("/")
                }

            }
        }
        return responsesModelName
    }

    /**
     * Поиск обязательных параметров для запроса в path
     * @return MutableMap<String, String> - имя параметра и тип параметра
     */
    fun requiredParameters(action: Map.Entry<PathItem.HttpMethod, Operation>): MutableMap<String, String> {
        val mapParameters = mutableMapOf<String, String>()
        if (action.value.parameters != null) {
            action.value.parameters.forEach { parameters ->
                parameters.takeIf { parameters.`in`.equals("path") && parameters.required == true }
                    ?.apply {
                        mapParameters[parameters.name] = parameters.schema.type
                    }
            }
        }
        return mapParameters
    }

    /**
     * Поиск обязательных параметров для запроса в header
     * @return MutableMap<String, String> - имя параметра и тип параметра
     */
    fun requiredHeaders(action: Map.Entry<PathItem.HttpMethod, Operation>): MutableMap<String, String> {
        val mapHeader = mutableMapOf<String, String>()
        if (action.value.parameters != null) {
            action.value.parameters.forEach { parametersHeader ->
                parametersHeader.takeIf { parametersHeader.`in`.equals("header") && parametersHeader.required == true }
                    ?.apply {
                        mapHeader[parametersHeader.name] = parametersHeader.schema.type
                    }
            }
        }
        return mapHeader
    }

    /**
     * Поиск обязательных параметров для запроса в query
     * @return MutableMap<String, String> - имя параметра и тип параметра
     */
    fun requiredQueryParam(action: Map.Entry<PathItem.HttpMethod, Operation>): MutableMap<String, String> {
        val mapQueryParam = mutableMapOf<String, String>()
        if (action.value.parameters != null) {
            action.value.parameters.forEach { parametersQuery ->
                parametersQuery.takeIf { parametersQuery.`in`.equals("query") && parametersQuery.required == true }
                    ?.apply {
                        val queryType = if (parametersQuery.schema.type == null) {
                            "object"
                        } else {
                            parametersQuery.schema.type
                        }
                        mapQueryParam[parametersQuery.name] = queryType
                    }
            }
        }
        return mapQueryParam
    }

}