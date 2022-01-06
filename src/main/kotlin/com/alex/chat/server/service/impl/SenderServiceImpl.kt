package com.alex.chat.server.service.impl

import com.alex.chat.server.models.User
import com.alex.chat.server.service.SenderService
import com.alex.chat.server.services.UserMessageSender
import java.io.PrintWriter

class SenderServiceImpl : SenderService {
    override fun createSender(writer: PrintWriter, user: User) = UserMessageSender(writer, user)
}