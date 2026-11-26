package repoanalyzer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.charset.StandardCharsets
import java.nio.file.Path

class MainTest {
    @TempDir
    lateinit var root: Path

    @Test
    fun `prints both reports for a valid directory`() {
        root.writeUtf8("src/Main.kt", "fun main() {}")
        val output = ByteArrayOutputStream()
        val error = ByteArrayOutputStream()

        val exitCode = run(
            arrayOf(root.toString()),
            PrintStream(output, true, StandardCharsets.UTF_8),
            PrintStream(error, true, StandardCharsets.UTF_8),
        )

        assertEquals(0, exitCode)
        assertTrue(output.toString(StandardCharsets.UTF_8).contains("Files and lines by extension:"))
        assertTrue(output.toString(StandardCharsets.UTF_8).contains("kt | 1 | 1"))
        assertTrue(output.toString(StandardCharsets.UTF_8).contains("src/Main.kt | 1"))
        assertEquals("", error.toString(StandardCharsets.UTF_8))
    }

    @Test
    fun `returns usage error when argument is missing`() {
        val output = ByteArrayOutputStream()
        val error = ByteArrayOutputStream()

        val exitCode = run(
            emptyArray(),
            PrintStream(output, true, StandardCharsets.UTF_8),
            PrintStream(error, true, StandardCharsets.UTF_8),
        )

        assertEquals(1, exitCode)
        assertTrue(error.toString(StandardCharsets.UTF_8).contains("Usage:"))
        assertEquals("", output.toString(StandardCharsets.UTF_8))
    }

    @Test
    fun `returns a clear error when path is a file`() {
        val file = root.writeUtf8("not-a-directory.txt", "content")
        val output = ByteArrayOutputStream()
        val error = ByteArrayOutputStream()

        val exitCode = run(
            arrayOf(file.toString()),
            PrintStream(output, true, StandardCharsets.UTF_8),
            PrintStream(error, true, StandardCharsets.UTF_8),
        )

        assertEquals(1, exitCode)
        assertTrue(error.toString(StandardCharsets.UTF_8).contains("not an existing directory"))
        assertEquals("", output.toString(StandardCharsets.UTF_8))
    }

    @Test
    fun `returns a clear error when path does not exist`() {
        val missingPath = root.resolve("missing-directory")
        val output = ByteArrayOutputStream()
        val error = ByteArrayOutputStream()

        val exitCode = run(
            arrayOf(missingPath.toString()),
            PrintStream(output, true, StandardCharsets.UTF_8),
            PrintStream(error, true, StandardCharsets.UTF_8),
        )

        assertEquals(1, exitCode)
        assertTrue(error.toString(StandardCharsets.UTF_8).contains("not an existing directory"))
        assertEquals("", output.toString(StandardCharsets.UTF_8))
    }
}
