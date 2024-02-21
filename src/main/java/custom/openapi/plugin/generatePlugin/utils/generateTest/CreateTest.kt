package custom.openapi.plugin.generatePlugin.utils.generateTest

import com.google.gson.Gson
import io.swagger.v3.parser.OpenAPIV3Parser
import custom.openapi.plugin.Extensions
import java.io.File
import java.util.*


class CreateTest {

    /**
     * Создание конфига из Swagger документации для создания классов с тестами.
     *
     * @param .
     * @param .
     */
    fun createTest(extensions: Extensions, pathToProject: String) {
        File("${pathToProject}/${extensions.specToGenerate}").walkTopDown().maxDepth(1)
            .filter { file ->
                file.isFile && file.name.contains(Regex("^.*.yaml$|^.*.yml$"))
            }.forEach { fileSpec ->
                val openAPI = OpenAPIV3Parser().read(fileSpec.path)
                //Find required fields in components models
                val requiredFieldInModels = ParseSwaggerSpecSchemas().searchRequiredFieldInBody(openAPI)
                val configForTest = GenerateTestBySpec()
                var mapParameters = mutableMapOf<String, String>()
                var mapHeader = mutableMapOf<String, String>()
                var mapQueryParam = mutableMapOf<String, String>()
                openAPI.paths.keys.forEach { method ->
                    openAPI.paths[method]?.readOperationsMap()?.forEach { action ->
                        action.value.takeIf { r -> r.deprecated == null && (r.tags == null || !r.tags.contains("RabbitMQ")) }
                            ?.apply {
                                //Find requestModelName
                                val requestModelName = ParseSwaggerSpecMethods().requestModelName(action)

                                //Find responsesModelName
                                val responsesModelName = ParseSwaggerSpecMethods().responsesModelName(action)

                                //Required parameters field
                                mapParameters = ParseSwaggerSpecMethods().requiredParameters(action)

                                //Find required Header field
                                mapHeader = ParseSwaggerSpecMethods().requiredHeaders(action)

                                //Find required query fields
                                mapQueryParam = ParseSwaggerSpecMethods().requiredQueryParam(action)

                                configForTest.componentId = fileSpec.name.lowercase(Locale.getDefault()).substringBeforeLast(".")
                                configForTest.version = openAPI.info.version
                                configForTest.methods?.add(
                                    GenerateTestBySpec.Paths(
                                        methodAction = action.key.name,
                                        endPoint = method,
                                        methodName = action.value.operationId,
                                        requestModelName = requestModelName,
                                        responsesModelName = responsesModelName,
                                        requiredField = GenerateTestBySpec.Paths.RequiredField(
                                            body = addValueToBody(requiredFieldInModels[requestModelName]),
                                            header = addValueToHeader(mapHeader),
                                            query = addValueToQuery(mapQueryParam),
                                            parameters = addValueToParameter(mapParameters)
                                        )
                                    )
                                )
//                                UtilsToCreateTest().createFolderAndFileWithTests(fileSpec, configForTest)
                            }
                        mapHeader.clear()
                        mapParameters.clear()
                    }
                }
                CreateTestScenarios().parseSwaggerConfigAndCreateTests(
                    fileSpec,
                    Gson().toJson(configForTest),
                    pathToProject
                )
            }

    }
}

//fun main() {
//    File("spec2").walkTopDown().maxDepth(1)
//        .filter { file ->
//            file.isFile && file.name.contains(Regex("^.*.yaml$|^.*.yml$"))
//        }.forEach { fileSpec ->
//            val openAPI = OpenAPIV3Parser().read(fileSpec.path)
//            //Find required fields in components models
//            val requiredFieldInModels = ParseSwaggerSpecSchemas().searchRequiredFieldInBody(openAPI)
//            val configForTest = GenerateTestBySpec()
//            var mapParameters = mutableMapOf<String, String>()
//            var mapHeader = mutableMapOf<String, String>()
//            var mapQueryParam = mutableMapOf<String, String>()
//            openAPI.paths.keys.forEach { method ->
//                openAPI.paths[method]?.readOperationsMap()?.forEach { action ->
//                    action.value.takeIf { r -> r.deprecated == null && (r.tags == null || !r.tags.contains("RabbitMQ")) }
//                        ?.apply {
//                            println(action.key)
//                            //Find requestModelName
//                            val requestModelName = ParseSwaggerSpecMethods().requestModelName(action)
//
//                            //Find responsesModelName
//                            val responsesModelName = ParseSwaggerSpecMethods().responsesModelName(action)
//
//                            //Required parameters field
//                            mapParameters = ParseSwaggerSpecMethods().requiredParameters(action)
//
//                            //Find required Header field
//                            mapHeader = ParseSwaggerSpecMethods().requiredHeaders(action)
//
//                            //Find required query fields
//                            mapQueryParam = ParseSwaggerSpecMethods().requiredQueryParam(action)
//
//                            configForTest.componentId = fileSpec.name.toLowerCase().substringBeforeLast(".")
//                            configForTest.version = openAPI.info.version
//                            configForTest.methods?.add(
//                                GenerateTestBySpec.Paths(
//                                    methodAction = action.key.name,
//                                    endPoint = method,
//                                    methodName = action.value.operationId,
//                                    requestModelName = requestModelName,
//                                    responsesModelName = responsesModelName,
//                                    requiredField = GenerateTestBySpec.Paths.RequiredField(
//                                        body = addValueToBody(requiredFieldInModels[requestModelName]),
//                                        header = addValueToHeader(mapHeader),
//                                        query = addValueToQuery(mapQueryParam),
//                                        parameters = addValueToParameter(mapParameters)
//                                    )
//                                )
//                            )
//                            UtilsToCreateTest().createFolderAndFileWithTests(fileSpec, configForTest)
//                        }
//                    mapHeader.clear()
//                    mapParameters.clear()
//                }
//            }
//            CreateTestScenarios().parseSwaggerConfigAndCreateTests(fileSpec, Gson().toJson(configForTest))
//        }
//}