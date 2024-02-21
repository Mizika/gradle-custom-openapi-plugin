package custom.openapi.plugin.generatePlugin.utils.generateTest

data class GenerateTestBySpec(
    var componentId: String? = "",
    var version: String? = "",
    var methods: ArrayList<Paths>? = arrayListOf()
) {
    data class Paths(
        var methodAction: String? = "",
        var endPoint: String? = "",
        var methodName: String? = "",
        var requestModelName: String? = "",
        var responsesModelName: String? = "",
        var requiredField: RequiredField? = RequiredField(),
    ) {
        data class RequiredField(
            var body: ArrayList<Body>? = arrayListOf(),
            var header: ArrayList<Header>? = arrayListOf(),
            var parameters: ArrayList<Parameter>? = arrayListOf(),
            var query: ArrayList<Query>? = arrayListOf()
        ) {
            data class Body(
                var name: String? = ""
            )

            class Header(
                var name: String? = "",
                var type: String? = ""
            )

            class Parameter(
                var name: String? = "",
                var type: String? = ""
            )

            class Query(
                var name: String? = "",
                var type: String? = ""
            )
        }
    }
}


fun addValueToBody(
    arrayRequiredFields: MutableList<String>?,
): ArrayList<GenerateTestBySpec.Paths.RequiredField.Body> {
    val arrayListElementInBody: ArrayList<GenerateTestBySpec.Paths.RequiredField.Body> = arrayListOf()
    if (arrayRequiredFields != null) {
        for (name in arrayRequiredFields) {
            arrayListElementInBody.add(GenerateTestBySpec.Paths.RequiredField.Body(name = name))
        }
    }
    return arrayListElementInBody
}

fun addValueToHeader(
    mapHeader: MutableMap<String, String>,
): ArrayList<GenerateTestBySpec.Paths.RequiredField.Header> {
    val arrayListElementInHeader: ArrayList<GenerateTestBySpec.Paths.RequiredField.Header> = arrayListOf()
    for ((name, type) in mapHeader) {
        arrayListElementInHeader.add(GenerateTestBySpec.Paths.RequiredField.Header(name = name, type = type))
    }
    return arrayListElementInHeader
}

fun addValueToQuery(
    mapQuery: MutableMap<String, String>,
): ArrayList<GenerateTestBySpec.Paths.RequiredField.Query> {
    val arrayListElementInQuery: ArrayList<GenerateTestBySpec.Paths.RequiredField.Query> = arrayListOf()
    for ((name, type) in mapQuery) {
        arrayListElementInQuery.add(GenerateTestBySpec.Paths.RequiredField.Query(name = name, type = type))
    }
    return arrayListElementInQuery
}

fun addValueToParameter(
    mapParam: MutableMap<String, String>,
): ArrayList<GenerateTestBySpec.Paths.RequiredField.Parameter> {
    val arrayListElementInParameter: ArrayList<GenerateTestBySpec.Paths.RequiredField.Parameter> = arrayListOf()
    for ((name, type) in mapParam) {
        arrayListElementInParameter.add(GenerateTestBySpec.Paths.RequiredField.Parameter(name = name, type = type))
    }
    return arrayListElementInParameter
}