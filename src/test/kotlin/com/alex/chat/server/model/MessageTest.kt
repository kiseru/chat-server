package com.alex.chat.server.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MessageTest {
    private val messageText = "Some message"

    @Test
    fun `test default from`() {
        val message = Message(messageText)
        val declaredField = Message::class.java.getDeclaredField("from")
        declaredField.isAccessible = true
        val from = declaredField[message] as String
        Assertions.assertEquals("Сервер", from)
    }

    @Test
    fun `test toString`() {
        val from = "Some author"
        val message = Message(from, messageText)
        val toStringResult = message.toString()
        val expected = String.format("%s::%s", from, messageText)
        Assertions.assertEquals(toStringResult, expected)
    }
}