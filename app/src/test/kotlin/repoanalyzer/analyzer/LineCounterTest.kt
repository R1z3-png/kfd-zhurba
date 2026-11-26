package repoanalyzer.analyzer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import repoanalyzer.writeUtf8
import java.nio.file.Path

class LineCounterTest {
    @TempDir
    lateinit var directory: Path

    private val counter = LineCounter()

    @Test
    fun `counts an empty file as zero lines`() {
        val file = directory.writeUtf8("empty.kt", "")
        assertEquals(0, counter.count(file.toFile()))
    }

    @Test
    fun `counts lines including blank lines`() {
        val file = directory.writeUtf8("lines.kt", "first\n\nthird\n")
        assertEquals(3, counter.count(file.toFile()))
    }

    @Test
    fun `counts a final line without a line terminator`() {
        val file = directory.writeUtf8("unterminated.kt", "abc")
        assertEquals(1, counter.count(file.toFile()))
    }

    @Test
    fun `reads UTF-8 text`() {
        val file = directory.writeUtf8("utf8.md", "Привет\nмир")
        assertEquals(2, counter.count(file.toFile()))
    }
}
