package com.alex.chat.server.model

import kotlinx.coroutines.flow.asFlow
import java.util.concurrent.CopyOnWriteArraySet

class Group(
    val name: String,
) {

    private val users = CopyOnWriteArraySet<User>()

    suspend fun addUser(user: User) {
        users.add(user)
        user.logJoiningToGroup(name)
    }

    suspend fun sendMessage(message: Message) {
        users.asFlow()
            .collect {
                it.sendMessage(message)
            }
    }
}