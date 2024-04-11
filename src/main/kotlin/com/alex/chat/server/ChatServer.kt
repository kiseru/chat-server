package com.alex.chat.server

import com.alex.chat.server.model.User
import com.alex.chat.server.service.GroupService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.InputStream
import java.io.OutputStream
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
        val outputStream = socket.getOutputStream()

        val user = authorizeUser(inputStream)
        launch {
            log.info("Running new message receiver for user ${user.name} of group ${user.group.name}")
            runReceiver(inputStream, user)
        }
        launch {
            log.info("Running new message sender for user ${user.name} of group ${user.group.name}")
            runSender(outputStream, user)
        }
    }

    private suspend fun authorizeUser(inputStream: InputStream): User {
        val reader = inputStream.bufferedReader()
        val userName = withContext(Dispatchers.IO) {
            reader.readLine()
        }
        val groupName = withContext(Dispatchers.IO) {
            reader.readLine()
        }
        return groupService.addUserToGroup(userName, groupName)
    }

    private suspend fun runReceiver(inputStream: InputStream, user: User) = coroutineScope {
        val reader = inputStream.bufferedReader()
        while (true) {
            val input = reader.readLine() ?: break
            user.sendMessageToGroup(input)
        }
    }

    private suspend fun runSender(outputStream: OutputStream, user: User) = coroutineScope {
        val writer = outputStream.bufferedWriter()
        while (true) {
            val message = user.pollMessage()
            writer.append(message.toString())
            writer.newLine()
            writer.flush()
        }
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(ChatServer::class.java)
    }
}
