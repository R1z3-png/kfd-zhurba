package repoanalyzer

import repoanalyzer.analyzer.RepositoryAnalyzer
import repoanalyzer.report.ReportFormatter
import java.io.File
import java.io.PrintStream
import kotlin.system.exitProcess

/** Command-line entry point for the repository analyzer. */
fun main(args: Array<String>) {
    val exitCode = run(args, System.out, System.err)
    if (exitCode != 0) exitProcess(exitCode)
}

/** Runs the CLI and returns its process exit code; streams are injectable for tests. */
internal fun run(args: Array<String>, out: PrintStream, err: PrintStream): Int {
    if (args.size != 1) {
        err.println("Usage: repo-analyzer <directory>")
        return 1
    }

    val root = File(args.single())
    if (!root.exists() || !root.isDirectory) {
        err.println("Error: '${root.path}' is not an existing directory.")
        return 1
    }

    try {
        val result = RepositoryAnalyzer().analyze(root)
        val formatter = ReportFormatter()

        out.println("Files and lines by extension:")
        out.println(formatter.formatExtensionReport(result))
        out.println()
        out.println("Three longest files:")
        out.println(formatter.formatLongestFilesReport(result))
        return 0
    } catch (exception: Exception) {
        err.println("Error analyzing '${root.path}': ${exception.message ?: "unexpected error"}")
        return 1
    }
}
