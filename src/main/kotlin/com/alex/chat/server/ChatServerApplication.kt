package com.alex.chat.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

fun main(args: Array<String>) {
    val context = runApplication<ChatServerApplication>(*args)
    val chatServer = context.getBean(ChatServer::class.java)
    chatServer.run()
}

@SpringBootApplication
class ChatServerApplication
