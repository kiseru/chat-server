package com.alex.chat.server.model

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class User(
    val name: String,
) {

    private val messages = MutableSharedFlow<Message>(1, 1, BufferOverflow.DROP_OLDEST)

    suspend fun sendMessage(message: Message) {
        messages.emit(message)
    }

    fun messages(): Flow<Message> =
        messages
}
