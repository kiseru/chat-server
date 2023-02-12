package com.alex.chat.server

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

fun main(args: Array<String>) = runBlocking(Dispatchers.IO) {
    val context = runApplication<ChatServerApplication>(*args)
    val chatServer = context.getBean(ChatServer::class.java)
    chatServer.run()
}

@SpringBootApplication
class ChatServerApplication
