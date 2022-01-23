package com.alex.chat.server.service

import com.alex.chat.server.model.User
import com.alex.chat.server.service.impl.UserMessageReceiverImpl
import java.io.BufferedReader

interface ReceiverService {
    fun createReceiver(reader: BufferedReader, user: User): UserMessageReceiverImpl
}
