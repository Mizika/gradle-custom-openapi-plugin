package custom.openapi.plugin.generatePlugin.utils.generateTest

import com.google.gson.Gson
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.util.*

class UtilsToCreateTest {

    /**
     * Тестовый метод для сохранения json файла со сценарием.
     */
    fun createFolderAndFileWithTests(fileSpec: File, configForTest: GenerateTestBySpec) {
        val testConfigFolder = "${System.getProperty("user.dir")}/a_test_config"
        File(testConfigFolder).mkdir()
        try {
            PrintWriter(
                FileWriter(
                    "${testConfigFolder}/${
                        fileSpec.name.lowercase(Locale.getDefault()).substringBeforeLast(".")
                    }.json"
                )
            ).use {
                val gson = Gson()
                val jsonString = gson.toJson(configForTest)
                it.write(jsonString)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
