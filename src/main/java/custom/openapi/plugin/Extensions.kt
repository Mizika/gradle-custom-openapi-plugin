package custom.openapi.plugin

open class Extensions {
    /**
     * Параметр для указания расположения swagger-documentation
     */
    open var specToGenerate: String = ""

    /**
     * Параметр отвечающий за генерацию папки utils в test
     */
    open var utils: Boolean = false

    /**
     * Параметр для указания расположения TestConfig для создания бинов.
     * Путь начинается от project.projectDir.absolutePath
     */
    open var testConfig: String = ""

    open var download = mutableSetOf<String>()

    open var pathToSaveSpec: String = ""

    /**
     * Параметр отвечающий за создание хелпера с методами по указанному пути
     * Путь начинается от project.projectDir.absolutePath
     */
    open var helpers: String = ""

    /**
     * Параметр для удаления тега RabbitMq из swagger спецификации
     */
    open var deleteRabbitMq: Boolean = true

    /**
     * Параметр для удаления устаревших методов из swagger спецификации
     */
    open var deleteDeprecatedMethod: Boolean = true

    /**
     * Параметр для испправления имени имплементируемого DTO
     */
    open var fixXImplementation: Boolean = true

    /**
     * Параметр для указания пути темплейтов для генерации клиентов моделей
     */
    open var templateDir: String = ""

    /**
     * Параметр для удаления атрибутов readOnly из swagger спецификации
     */
    open var deleteReadOnlyParam: Boolean = false

    /**
     * Параметр для удаления атрибутов writeOnly из swagger спецификации
     */
    open var deleteWriteOnlyParam: Boolean = false


}