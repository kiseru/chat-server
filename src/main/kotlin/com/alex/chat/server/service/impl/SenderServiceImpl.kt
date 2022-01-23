package com.alex.chat.server.service.impl

import com.alex.chat.server.model.User
import com.alex.chat.server.service.SenderService
import org.springframework.stereotype.Component
import java.io.PrintWriter

@Component
class SenderServiceImpl : SenderService {
    override fun createSender(writer: PrintWriter, user: User) = UserMessageSenderImpl(writer, user)
}