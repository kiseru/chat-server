package com.alex.chat.server.model

import kotlinx.coroutines.channels.Channel

class User(
    val name: String,
    val group: Group,
) {

    private val channel = Channel<Message>(Channel.BUFFERED)

    suspend fun sendMessage(message: Message) {
        channel.send(message)
    }

    suspend fun pollMessage(): Message {
        return channel.receive()
    }

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