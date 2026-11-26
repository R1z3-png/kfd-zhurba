package repoanalyzer

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
/** тестовый помощник. Создаёт файл в нужном месте тестового временного каталога */
internal fun Path.writeUtf8(relativePath: String, contents: String): Path {
    val file = resolve(relativePath)
    Files.createDirectories(file.parent)
    return Files.writeString(file, contents, StandardCharsets.UTF_8)
}
