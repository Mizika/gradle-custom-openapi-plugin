package custom.openapi.plugin.generatePlugin.utils

import org.gradle.api.InvalidUserDataException
import custom.openapi.plugin.Extensions
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels

class Download {


    fun downloadAndSaveSpecification(extensions: Extensions, pathToProject: String) {
        if (extensions.download.isNotEmpty()) {
            extensions.pathToSaveSpec.isNotEmpty() ||
                    throw InvalidUserDataException("If you are using download, you need to specify pathToSaveSpec = \"....\"")
            extensions.download.forEach { dataToDownload ->
                Download().downloadFile(
                    url = URL(dataToDownload),
                    pathToSave = extensions.pathToSaveSpec,
                    pathToProject
                )
            }
        }
    }

    fun downloadFile(url: URL, pathToSave: String, pathToProject: String) {
        File("${pathToProject}/${pathToSave}").mkdir()
        url.openStream().use {
            Channels.newChannel(it).use { rbc ->
                FileOutputStream(
                    "${pathToProject}/" +
                            "${pathToSave}/${url.toString().substringAfterLast("/")}"
                ).use { fos ->
                    fos.channel.transferFrom(rbc, 0, Long.MAX_VALUE)
                }
            }
        }
    }
}
