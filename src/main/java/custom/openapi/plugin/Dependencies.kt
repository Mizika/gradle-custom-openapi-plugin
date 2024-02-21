package custom.openapi.plugin

import org.gradle.api.Project

class Dependencies {

    private val implementation = "implementation"

    private val jacksonDatabind = "0.2.1"
    private val javaxAnnotation = "1.3.2"
    private val jacksonDatatypeJsr310 = "2.12.1"
    private val swaggerAnnotations = "1.6.2"
    private val jsr305 = "3.0.0"


    fun addDependenciesToProject(project: Project) {
        project.afterEvaluate {
            project.dependencies.add(
                implementation,
                "org.openapitools:jackson-databind-nullable:$jacksonDatabind"
            )
            project.dependencies.add(implementation, "javax.annotation:javax.annotation-api:$javaxAnnotation")
            project.dependencies.add(
                implementation,
                "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonDatatypeJsr310"
            )
            project.dependencies.add(implementation, "io.swagger:swagger-annotations:$swaggerAnnotations")
            project.dependencies.add(implementation, "com.google.code.findbugs:jsr305:$jsr305")
        }
    }
}