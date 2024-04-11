package com.alex.chat.server.model

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class User(
    val name: String,
    val group: Group,
) {

    private val messages = MutableSharedFlow<Message>(1, 1, BufferOverflow.DROP_OLDEST)

    suspend fun sendMessage(message: Message) {
        messages.emit(message)
    }

    fun messages(): Flow<Message> =
        messages

    suspend fun logJoiningToGroup(groupName: String) {
        val text = "$name добавился в группу $groupName"
        val message = Message(text)
        group.sendMessage(message)
    }

    suspend fun sendMessageToGroup(text: String) {
        val message = Message(name, text)
        group.sendMessage(message)
    }
}
