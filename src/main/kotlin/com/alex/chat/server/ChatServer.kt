package com.alex.chat.server

import com.alex.chat.server.model.User
import com.alex.chat.server.service.GroupService
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

@Component
class ChatServer(
    private val groupService: GroupService,
) {

    suspend fun run() = coroutineScope {
        val server = ServerSocket(5003)
        while (!server.isClosed) {
            val socket = server.accept()
            launch {
                log.info("Accepting new connection")
                handleConnection(socket)
            }
        }
    }

    private suspend fun handleConnection(socket: Socket) = coroutineScope {
        val inputStream = socket.getInputStream()
        val reader = BufferedReader(InputStreamReader(inputStream))
        val outputStream = socket.getOutputStream()
        val writer = PrintWriter(outputStream)

        val user = authorizeUser(reader)
        launch {
            log.info("Running new message receiver for user ${user.name} of group ${user.group.name}")
            runReceiver(reader, user)
        }
        launch {
            log.info("Running new message sender for user ${user.name} of group ${user.group.name}")
            runSender(writer, user)
        }
    }

    private suspend fun authorizeUser(reader: BufferedReader): User {
        val userName = reader.readLine()
        val groupName = reader.readLine()
        return groupService.addUserToGroup(userName, groupName)
    }

    private suspend fun runReceiver(reader: BufferedReader, user: User) = coroutineScope {
        while (true) {
            val input = reader.readLine() ?: break
            user.sendMessageToGroup(input)
        }
    }

    private suspend fun runSender(writer: PrintWriter, user: User) = coroutineScope {
        while (true) {
            val message = user.pollMessage()
            writer.println(message)
            writer.flush()
        }
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(ChatServer::class.java)
    }
}
