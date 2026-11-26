package repoanalyzer.analyzer

import java.io.File
import java.nio.file.Files

/** Finds supported source and documentation files under a root directory. */
class SourceFileCollector {
    fun collect(root: File): List<File> {
        val excludedDirectories = setOf(".git", ".gradle", "build")
        val supportedExtensions = setOf("kt", "kts", "md")

        return root.walkTopDown()
            .onEnter { directory ->
                !Files.isSymbolicLink(directory.toPath()) && directory.name !in excludedDirectories
            }
            .filter { file ->
                file.isFile &&
                    !Files.isSymbolicLink(file.toPath()) &&
                    file.extension in supportedExtensions
            }
            .toList()
    }
}
