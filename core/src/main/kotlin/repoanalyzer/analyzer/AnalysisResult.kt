package repoanalyzer.analyzer


data class ExtensionSummary(
    val extension: String,
    val fileCount: Int,
    val totalLines: Int,
)

data class FileLineCount(
    val relativePath: String,
    val lineCount: Int,
)

data class AnalysisResult(
    val extensionSummaries: List<ExtensionSummary>,
    val longestFiles: List<FileLineCount>,
)
