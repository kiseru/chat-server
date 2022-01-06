package com.alex.chat.server.service.impl

import com.alex.chat.server.models.User
import com.alex.chat.server.service.ReceiverService
import com.alex.chat.server.services.UserMessageReceiver
import java.io.BufferedReader

class ReceiverServiceImpl : ReceiverService {
    override fun createReceiver(reader: BufferedReader, user: User) = UserMessageReceiver(reader, user)
}