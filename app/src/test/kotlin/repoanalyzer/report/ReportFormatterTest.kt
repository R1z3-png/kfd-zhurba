package repoanalyzer.report

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import repoanalyzer.analyzer.AnalysisResult
import repoanalyzer.analyzer.ExtensionSummary
import repoanalyzer.analyzer.FileLineCount

class ReportFormatterTest {
    private val formatter = ReportFormatter()

    @Test
    fun `formats extension totals alphabetically`() {
        val result = AnalysisResult(
            extensionSummaries = listOf(
                ExtensionSummary("md", 1, 0),
                ExtensionSummary("kt", 2, 4),
            ),
            longestFiles = emptyList(),
        )

        assertEquals(
            "Extension | Files | Lines\nkt | 2 | 4\nmd | 1 | 0",
            formatter.formatExtensionReport(result),
        )
    }

    @Test
    fun `formats longest files by lines and path and limits output to three`() {
        val result = AnalysisResult(
            extensionSummaries = emptyList(),
            longestFiles = listOf(
                FileLineCount("z.kt", 5),
                FileLineCount("b.kt", 7),
                FileLineCount("a.kt", 7),
                FileLineCount("short.kt", 1),
            ),
        )

        assertEquals(
            "File | Lines\na.kt | 7\nb.kt | 7\nz.kt | 5",
            formatter.formatLongestFilesReport(result),
        )
    }

    @Test
    fun `formats empty reports as empty strings`() {
        val result = AnalysisResult(emptyList(), emptyList())

        assertEquals("", formatter.formatExtensionReport(result))
        assertEquals("", formatter.formatLongestFilesReport(result))
    }
}
