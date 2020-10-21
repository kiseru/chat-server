package com.alex.chat.server.models

data class Message(
    private val from: String,
    private val text: String
) {
    constructor(text: String) : this("Server", text)

    override fun toString(): String {
        return "$from::$text"
    }
}