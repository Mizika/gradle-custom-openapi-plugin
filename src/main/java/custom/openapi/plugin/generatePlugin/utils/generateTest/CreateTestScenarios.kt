package custom.openapi.plugin.generatePlugin.utils.generateTest

import com.google.gson.Gson
import java.io.File
import java.io.FileWriter
import java.util.*

class CreateTestScenarios {

    /**
     * Парсинг созданного конфига из Swagger документации и создание классов с тестами.
     * На данный момент создание тестов идет только по обязательным параметрам в методе.
     *
     * @param fileSpec файл со Swagger контрактом.
     * @param jsonConfig файл со сценарием.
     */
    fun parseSwaggerConfigAndCreateTests(fileSpec: File, jsonConfig: String, pathToProject: String) {
        val parentDirPath = "$pathToProject/src/test/java/ru/russianpost/testcases/negativeGenerate"
        if (!File(parentDirPath).exists()) {
            File(parentDirPath).mkdir()
        }
        val dir = File(parentDirPath, fileSpec.nameWithoutExtension.lowercase(Locale.getDefault()))
        if (!File("${dir}/${fileSpec.nameWithoutExtension.capitalize()}GeneratedTests.java").exists()) {
            if (!dir.exists()) {
                dir.mkdir()
            }
            val mU = Gson().fromJson(jsonConfig, GenerateTestBySpec::class.java)
            File("${dir}/${fileSpec.nameWithoutExtension.capitalize()}GeneratedTests.java").bufferedWriter()
                .use { out ->
                    out.write("package ru.russianpost.testcases.negativeGenerate.${fileSpec.nameWithoutExtension.lowercase(
                        Locale.getDefault()
                    )};")
                    out.newLine()
                    out.newLine()
                    out.write(
                        "import io.qameta.allure.Description;\n" +
                                "import org.junit.jupiter.api.Test;\n" +
                                "import ru.russianpost.utils.TestBase;\n"
                    )
                    out.newLine()
                    out.newLine()
                    out.write("//Scenario by openApi doc \"${mU.componentId?.toUpperCase()}\"")
                    out.newLine()
                    out.write("//Version spec: ${mU.version?.toUpperCase()}")
                    out.newLine()
                    out.write("public class ${fileSpec.nameWithoutExtension.capitalize()}GeneratedTests extends TestBase {")
                    out.newLine()
                    out.newLine()
                }
            mU.methods?.forEach { methods ->
                FileWriter(
                    "${dir}/${fileSpec.nameWithoutExtension.capitalize()}GeneratedTests.java",
                    true
                ).use { write ->
                    if (methods.requiredField?.body?.size!! > 0) {
                        methods.requiredField?.body?.forEach { bodyFields ->
                            write.write(
                                "    @Test\n" +
                                        "    @Description(\"В методе [${methods.methodAction} ${methods.endPoint}] " +
                                        "в теле запроса остуствует обязательное поле \\\"${bodyFields.name}\\\"\")\n" +
                                        "    public void ${methods.methodAction?.lowercase(Locale.getDefault())}${
                                            methods.endPoint?.replace(
                                                "/",
                                                ""
                                            )?.replace("{", "")?.replace("}", "")?.replace("-", "")?.replace(".", "")
                                                ?.capitalize()
                                        }WithOut${bodyFields.name?.capitalize()?.replace("-", "")}InBody(){}"
                            )
                            write.appendLine()
                            write.appendLine()
                        }
                    }
                    if (methods.requiredField?.header?.size!! > 0) {
                        methods.requiredField?.header?.forEach { headerFields ->
                            write.write(
                                "    @Test\n" +
                                        "    @Description(\"В методе [${methods.methodAction} ${methods.endPoint}] " +
                                        "в header запроса остуствует обязательное поле \\\"${headerFields.name}\\\"\")\n" +
                                        "    public void ${methods.methodAction?.toLowerCase()}${
                                            methods.endPoint?.replace(
                                                "/",
                                                ""
                                            )?.replace("{", "")?.replace("}", "")?.replace("-", "")?.replace(".", "")
                                                ?.capitalize()
                                        }WithOut${headerFields.name?.capitalize()?.replace("-", "")}InHeader(){}"
                            )
                            write.appendLine()
                            write.appendLine()
                        }
                    }
                    if (methods.requiredField?.parameters?.size!! > 0) {
                        methods.requiredField?.parameters?.forEach { parametersFields ->
                            write.write(
                                "    @Test\n" +
                                        "    @Description(\"В методе [${methods.methodAction} ${methods.endPoint}] " +
                                        "в параметрах запроса остуствует обязательное поле \\\"${parametersFields.name}\\\"\")\n" +
                                        "    public void ${methods.methodAction?.toLowerCase()}${
                                            methods.endPoint?.replace(
                                                "/",
                                                ""
                                            )?.replace("{", "")?.replace("}", "")?.replace("-", "")?.replace(".", "")
                                                ?.capitalize()
                                        }WithOut${
                                            parametersFields.name?.capitalize()?.replace("-", "")
                                        }InParameters(){}"
                            )
                            write.appendLine()
                            write.appendLine()
                        }
                    }
                    if (methods.requiredField?.query?.size!! > 0) {
                        methods.requiredField?.query?.forEach { queryFields ->
                            write.write(
                                "    @Test\n" +
                                        "    @Description(\"В методе [${methods.methodAction} ${methods.endPoint}] " +
                                        "в query запроса остуствует обязательное поле \\\"${queryFields.name}\\\"\")\n" +
                                        "    public void ${methods.methodAction?.toLowerCase()}${
                                            methods.endPoint?.replace(
                                                "/",
                                                ""
                                            )?.replace("{", "")?.replace("}", "")?.replace("-", "")?.replace(".", "")
                                                ?.capitalize()
                                        }WithOut${queryFields.name?.capitalize()?.replace("-", "")}InQuery(){}"
                            )
                            write.appendLine()
                            write.appendLine()
                        }
                    }
                }
            }
            FileWriter(
                "${dir}/${fileSpec.nameWithoutExtension.capitalize()}GeneratedTests.java",
                true
            ).use { write ->
                write.write("}")
            }
        }
    }
}