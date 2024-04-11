package com.alex.chat.server.model

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow

class User(
    val name: String,
    val group: Group,
) {

    private val channel = Channel<Message>(Channel.BUFFERED)

    suspend fun sendMessage(message: Message) {
        channel.send(message)
    }

    fun messages(): Flow<Message> =
        channel.consumeAsFlow()

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
