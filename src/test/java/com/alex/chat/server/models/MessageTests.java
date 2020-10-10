package com.alex.chat.server.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageTests {
    private final String messageText = "Some message";

    @Test
    @DisplayName("test default from")
    public void testDefaultFrom() throws NoSuchFieldException, IllegalAccessException {
        Message message = new Message(messageText);
        Field declaredField = Message.class.getDeclaredField("from");
        declaredField.setAccessible(true);
        String from = (String) declaredField.get(message);
        assertEquals("Сервер", from);
    }

    @Test
    @DisplayName("test toString()")
    public void testToString() {
        String from = "Some author";
        Message message = new Message(from, messageText);
        String toStringResult = message.toString();
        String expected = String.format("%s::%s", from, messageText);
        assertEquals(toStringResult, expected);
    }
}