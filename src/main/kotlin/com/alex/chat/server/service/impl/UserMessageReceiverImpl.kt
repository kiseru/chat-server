package com.alex.chat.server.service.impl

import com.alex.chat.server.model.Message
import com.alex.chat.server.model.User
import com.alex.chat.server.service.UserMessageReceiver
import java.io.BufferedReader

class UserMessageReceiverImpl(
    private val reader: BufferedReader,
    private val user: User,
) : UserMessageReceiver {
    override fun run() {
        while (true) {
            val input = reader.readLine() ?: break
            val message = Message(user.name, input)
            val group = user.group
            group.sendMessage(message)
        }
    }
}
