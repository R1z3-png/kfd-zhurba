package repoanalyzer.analyzer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import repoanalyzer.writeUtf8
import java.nio.file.Path

class RepositoryAnalyzerTest {
    @TempDir
    lateinit var root: Path

    private val analyzer = RepositoryAnalyzer()

    @Test
    fun `matches the four-file example from the assignment`() {
        val sampleDirectory = Path.of(
            requireNotNull(javaClass.getResource("/repo-analyzer-sample")).toURI(),
        )

        val result = analyzer.analyze(sampleDirectory.toFile())

        assertEquals(
            listOf(ExtensionSummary("kt", 2, 4), ExtensionSummary("md", 1, 0)),
            result.extensionSummaries,
        )
        assertEquals(
            listOf(
                FileLineCount("src/A.kt", 2),
                FileLineCount("src/B.kt", 2),
                FileLineCount("README.md", 0),
            ),
            result.longestFiles,
        )
    }

    @Test
    fun `returns empty result for an empty directory`() {
        assertEquals(AnalysisResult(emptyList(), emptyList()), analyzer.analyze(root.toFile()))
    }

    @Test
    fun `keeps only three longest files and sorts ties by relative path`() {
        root.writeUtf8("z.kt", "one\ntwo")
        root.writeUtf8("b.kt", "one\ntwo")
        root.writeUtf8("a.kt", "one\ntwo")
        root.writeUtf8("short.kt", "one")

        val result = analyzer.analyze(root.toFile())

        assertEquals(
            listOf(FileLineCount("a.kt", 2), FileLineCount("b.kt", 2), FileLineCount("z.kt", 2)),
            result.longestFiles,
        )
        assertEquals(listOf(ExtensionSummary("kt", 4, 7)), result.extensionSummaries)
    }
}
