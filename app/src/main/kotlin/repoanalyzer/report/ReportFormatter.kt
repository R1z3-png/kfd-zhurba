package repoanalyzer.report

import repoanalyzer.analyzer.AnalysisResult
import repoanalyzer.analyzer.FileLineCount

/** Formats the analyzer's results as the two requested text reports. */
class ReportFormatter {
    fun formatExtensionReport(result: AnalysisResult): String {
        if (result.extensionSummaries.isEmpty()) return ""

        return buildString {
            appendLine("Extension | Files | Lines")
            result.extensionSummaries
                .sortedBy { it.extension }
                .forEach { summary ->
                    appendLine("${summary.extension} | ${summary.fileCount} | ${summary.totalLines}")
                }
        }.trimEnd()
    }

    fun formatLongestFilesReport(result: AnalysisResult): String {
        if (result.longestFiles.isEmpty()) return ""

        return buildString {
            appendLine("File | Lines")
            result.longestFiles
                .sortedWith(compareByDescending<FileLineCount> { it.lineCount }.thenBy { it.relativePath })
                .take(3)
                .forEach { file ->
                    appendLine("${file.relativePath} | ${file.lineCount}")
                }
        }.trimEnd()
    }
}
