package com.alex.chat.server.service.impl

import com.alex.chat.server.model.User
import com.alex.chat.server.service.UserMessageSender
import java.io.PrintWriter

class UserMessageSenderImpl(
    private val writer: PrintWriter,
    private val user: User,
) : UserMessageSender {
    override fun run() {
        while (!Thread.currentThread().isInterrupted) {
            val message = user.pollMessage()
            writer.println(message)
            writer.flush()
        }
    }
}
