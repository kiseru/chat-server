package com.alex.chat.server.model

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class User(
    val name: String,
) {

    private val mutex = Mutex()
    private val messages = MutableSharedFlow<Message>(1, 1, BufferOverflow.DROP_OLDEST)

    private var isMessaging = true

    suspend fun sendMessage(message: Message) {
        messages.emit(message)
    }

    fun messages(): Flow<Message> =
        messages.takeWhile { isMessaging }

    suspend fun stopMessaging() {
        mutex.withLock { isMessaging = false }
        messages.emit(Message("stopped", name))
    }
}
