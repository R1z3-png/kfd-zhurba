package repoanalyzer.analyzer

import java.io.File

/** Counts text lines in a UTF-8 file. */
class LineCounter {
    fun count(file: File): Int = file.useLines(Charsets.UTF_8) { it.count() }
}
