package com.alex.chat.server.service.impl

import com.alex.chat.server.model.User
import com.alex.chat.server.service.ReceiverService
import org.springframework.stereotype.Component
import java.io.BufferedReader

@Component
class ReceiverServiceImpl : ReceiverService {
    override fun createReceiver(reader: BufferedReader, user: User) =
        UserMessageReceiverImpl(reader, user)
}