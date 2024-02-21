package custom.openapi.plugin.generatePlugin.utils

import java.io.File

class CreateBeanInTestConfig {


    fun createBeanInTestConfig(pathToClient: String, pathToTestConfig: String) {
        File(pathToClient)
            .walk(FileWalkDirection.BOTTOM_UP).filter { f -> f.name.contains(Regex(".*.java")) }.forEach { apiFile ->
                val clientName = apiFile.name.substringBefore(".")
                val lineWithName = "public static $clientName "
                val deleteCharacters = "(Supplier<RequestSpecBuilder> reqSpecSupplier) {"
                apiFile.forEachLine { line ->
                    if (line.contains(Regex("$lineWithName.*"))) {
                        val client = line.replace(lineWithName, "").replace(deleteCharacters, "")
                        insertBean(pathToTestConfig, clientName, createBean(clientName, client))
                        addImport(pathToTestConfig, apiFile)
                    }
                }
            }
    }

    fun createBeanHelper(pathToClient: String, pathToClientHelper: String) {
        File(pathToClient)
            .walk(FileWalkDirection.BOTTOM_UP).filter { f -> f.name.contains(Regex(".*.java")) }.forEach { apiFile ->
                val clientName = apiFile.name.substringBefore(".")
                val lineWithName = "public static $clientName "
                val deleteCharacters = "(Supplier<RequestSpecBuilder> reqSpecSupplier) {"
                if (!File(pathToClientHelper).exists()) {
                    File(pathToClientHelper).createNewFile()
                }
                val import = apiFile.path.replaceBefore("sw", "")
                    .replace("/", ".")
                    .replace("\\", ".")
                    .substringBeforeLast(".")
                if(!File(pathToClientHelper).readText().contains("io.restassured.builder.RequestSpecBuilder")){
                    File(pathToClientHelper).writeText(File(pathToClientHelper).readText() +
                            "===========================================REQUIRED IMPORTS===========================================\n" +
                            "import io.restassured.builder.RequestSpecBuilder;\n" +
                            "import io.restassured.filter.log.LogDetail;\n" +
                            "import io.restassured.filter.log.ResponseLoggingFilter;\n" +
                            "import java.util.UUID;\n" +
                            "import org.junit.jupiter.api.BeforeAll;\n" +
                            "import static io.restassured.RestAssured.config;\n" +
                            "import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;\n" +
                            "import static sw.generator.client.utils.JacksonObjectMapper.jackson;\n" +
                            "======================================================================================\n")
                }
                apiFile.forEachLine { line ->
                    if (line.contains(Regex("$lineWithName.*"))) {
                        val client = line.replace(lineWithName, "").replace(deleteCharacters, "")
                        if(!File(pathToClientHelper).readText().contains(clientName)){
                            File(pathToClientHelper).writeText(File(pathToClientHelper).readText()+"import ${import}\n")
                            File(pathToClientHelper).writeText(File(pathToClientHelper).readText()+createBeanToHelper(clientName, client))
                        }
                    }
                }
            }
    }

    private fun createBean(classname: String, clientName: String): String {
        val methodName = classname.decapitalize()
        return "    \n" +
                "    @Bean\n" +
                "    @Scope(\"prototype\")\n" +
                "    public $classname $methodName() {\n" +
                "        return $classname.$clientName(() -> getSpecBuilder(testConfig.getUrl().getTestUrl()));\n" +
                "    }"
    }

    private fun createBeanToHelper(classname: String, clientName: String): String {
        val methodName = classname.decapitalize()
        return "\n" +
                "    $classname $methodName;\n" +
                "\n" +
                "    @BeforeAll\n" +
                "    void client() {\n" +
                "        $methodName = $classname.$clientName(() -> new RequestSpecBuilder().log(LogDetail.ALL)\n" +
                "                .setConfig(config().objectMapperConfig(objectMapperConfig().defaultObjectMapper(jackson())))\n" +
                "                .addFilter(new ResponseLoggingFilter())\n" +
                "                .addHeader(\"X-CorrelationId\", UUID.randomUUID().toString())\n" +
                "                .setBaseUri(\"http://your.endpoint/\"));\n" +
                "    }\n"+
                "======================================================================================\n"
    }

    private fun insertBean(testConfigPath: String, clientName: String, addBean: String) {
        val testConfig = File(testConfigPath)
        if (!testConfig.readText().contains(clientName.decapitalize() + "()")) {
            val lastCharacter = testConfig.readText().lastIndexOf("}")
            testConfig.writeText(StringBuilder(testConfig.readText()).deleteAt(lastCharacter).toString())
            testConfig.writeText(testConfig.readText().trim() + "\n$addBean")
            testConfig.writeText(testConfig.readText().trim() + "\n}")
        }
    }

    private fun addImport(testConfigPath: String, apiFile: File) {
        val testConfig = File(testConfigPath)
        val import = apiFile.path.replaceBefore("sw", "")
            .replace("/", ".")
            .replace("\\", ".")
            .substringBeforeLast(".")
        if (!testConfig.readText().contains(import)) {
            testConfig.forEachLine { line ->
                if (line.contains(Regex("package.*"))) {
                    val foundPackageName = line
                    testConfig.writeText(testConfig.readText().replace(line, "$foundPackageName\n\nimport $import;"))
                }
            }
        }
    }
}