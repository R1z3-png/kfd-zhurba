package kfd

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

class MessagePolicyTest {
    @Test
    fun acceptsShortMessage() {
        assertTrue(canSendMessage("Привет"))
    }
    @Test
    fun denysLongMessageWithDefaultLimit() {
        assertFalse(canSendMessage("А".repeat(141)))
    }
    @Test
    fun acceptsLongMessageWOnDefaultLimitsBorder() {
        assertTrue(canSendMessage("А".repeat(140)))
    }
    @Test
    fun denysLongMessageWithShortLimit() {
        assertFalse(canSendMessage("Привет", maxLength = 1))
    }
    @Test
    fun handlesMessageOnLimitsBorder() {
        assertTrue(canSendMessage("Cat", maxLength = 3))
    }
    @Test
    fun denysMessageWithNegativeLimit() {
        assertFalse(canSendMessage("Привет", maxLength = -100))
    }
    @Test
    fun denysEmptyMessage() {
        assertFalse(canSendMessage(""))
    }
    @Test
    fun handlesNullMessage() {
        assertFalse(canSendMessage(null))
    }
    @Test
    fun denysSpacebarsMessage() {
        assertFalse(canSendMessage("   "))
    }
}