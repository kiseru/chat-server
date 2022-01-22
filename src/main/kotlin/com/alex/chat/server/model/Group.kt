package com.alex.chat.server.model

import com.alex.chat.server.models.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Group(
    private val name: String,
) {
    private val users = mutableSetOf<User>()

    fun addUser(user: User) {
        val message = "${user.name} добавился в группу"
        synchronized(users) {
            users.add(user)
            sendMessageFromServer(message)
        }
        log.info("$message $name")
    }

    fun sendMessage(message: Message) {
        for (user in users) {
            user.sendMessage(message)
        }
    }

    private fun sendMessageFromServer(text: String) {
        val message = Message(text)
        sendMessage(message)
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(Group::class.java)
    }
}