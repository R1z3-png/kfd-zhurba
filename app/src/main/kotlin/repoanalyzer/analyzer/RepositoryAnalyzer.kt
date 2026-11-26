package repoanalyzer.analyzer

import java.io.File

/** Coordinates file collection, line counting, and aggregation into report data. */
class RepositoryAnalyzer(
    private val sourceFileCollector: SourceFileCollector = SourceFileCollector(),
    private val lineCounter: LineCounter = LineCounter(),
) {
    /** Analyzes supported files below [root] and returns extension totals and the three longest files. */
    fun analyze(root: File): AnalysisResult {
        val analyzedFiles = sourceFileCollector.collect(root).map { file ->
            file.extension to FileLineCount(
                relativePath = file.relativeTo(root).invariantSeparatorsPath,
                lineCount = lineCounter.count(file),
            )
        }

        val extensionSummaries = analyzedFiles
            .groupBy(keySelector = { it.first }, valueTransform = { it.second })
            .map { (extension, files) ->
                ExtensionSummary(
                    extension = extension,
                    fileCount = files.size,
                    totalLines = files.sumOf { it.lineCount },
                )
            }
            .sortedBy { it.extension }

        val longestFiles = analyzedFiles
            .map { it.second }
            .sortedWith(compareByDescending<FileLineCount> { it.lineCount }.thenBy { it.relativePath })
            .take(3)

        return AnalysisResult(
            extensionSummaries = extensionSummaries,
            longestFiles = longestFiles,
        )
    }
}
