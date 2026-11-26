package repoanalyzer.analyzer

import java.io.File

/** Counts text lines in a UTF-8 file. */
class LineCounter {
    fun count(file: File): Int {
        return file.useLines(Charsets.UTF_8) { lines -> lines.count() }
    }
}
