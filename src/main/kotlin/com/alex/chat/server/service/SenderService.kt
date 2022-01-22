package com.alex.chat.server.service

import com.alex.chat.server.model.User
import com.alex.chat.server.services.UserMessageSender
import java.io.PrintWriter

interface SenderService {
    fun createSender(writer: PrintWriter, user: User): UserMessageSender
}
