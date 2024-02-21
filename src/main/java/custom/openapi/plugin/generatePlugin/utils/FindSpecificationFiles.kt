package custom.openapi.plugin.generatePlugin.utils

import java.io.File

open class FindSpecificationFiles {

    fun findSpec(pathToSpec: String, pathToProject: String): MutableList<File> {
        val nameSpecs: MutableList<File> = mutableListOf()
        File("$pathToProject/$pathToSpec").walkTopDown().maxDepth(1).forEach {
            if (it.name.contains(Regex("^.*.yaml$|^.*.yml$"))) {
                nameSpecs.add(it)
            }
        }
        return nameSpecs
    }

}