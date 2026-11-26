package repoanalyzer.analyzer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.api.Assumptions.assumeTrue
import repoanalyzer.writeUtf8
import java.nio.file.Files
import java.nio.file.Path

class SourceFileCollectorTest {
    @TempDir
    lateinit var root: Path

    private val collector = SourceFileCollector()

    @Test
    fun `collects supported files recursively and skips excluded directories and other extensions`() {
        root.writeUtf8("Top.kt", "class Top")
        root.writeUtf8("build.gradle.kts", "plugins {}")
        root.writeUtf8("docs/guide.md", "Guide")
        root.writeUtf8("notes.txt", "ignored")
        root.writeUtf8(".git/hidden.kt", "ignored")
        root.writeUtf8(".gradle/cache.kts", "ignored")
        root.writeUtf8("src/build/generated.md", "ignored")

        val paths = collector.collect(root.toFile())
            .map { it.relativeTo(root.toFile()).invariantSeparatorsPath }
            .toSet()

        assertEquals(setOf("Top.kt", "build.gradle.kts", "docs/guide.md"), paths)
    }

    @Test
    fun `does not collect symbolic links to files or directories`() {
        root.writeUtf8("real/Target.kt", "class Target")
        val fileLink = root.resolve("linked.kt")
        val directoryLink = root.resolve("linked-directory")

        try {
            Files.createSymbolicLink(fileLink, root.resolve("real/Target.kt"))
            Files.createSymbolicLink(directoryLink, root.resolve("real"))
        } catch (exception: Exception) {
            assumeTrue(false, "Symbolic links are unavailable in this environment: ${exception.message}")
        }

        val paths = collector.collect(root.toFile())
            .map { it.relativeTo(root.toFile()).invariantSeparatorsPath }
            .toSet()

        assertEquals(setOf("real/Target.kt"), paths)
    }
}
