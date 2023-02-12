package com.alex.chat.server.model

data class Message(
    val from: String,
    val text: String,
) {
    constructor(text: String) : this("Сервер", text)

    override fun toString(): String {
        return "$from::$text"
    }
}