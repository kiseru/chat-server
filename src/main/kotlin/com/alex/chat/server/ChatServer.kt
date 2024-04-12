package com.alex.chat.server

import com.alex.chat.server.model.Group
import com.alex.chat.server.model.Message
import com.alex.chat.server.model.User
import com.alex.chat.server.service.GroupService
import com.alex.chat.server.service.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.InputStream
import java.io.OutputStream
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException

@Component
class ChatServer(
    private val groupService: GroupService,
    private val userService: UserService,
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
        try {
            val inputStream = socket.getInputStream()
            val outputStream = socket.getOutputStream()

            val (username, groupName) = getUserAuthorities(inputStream)
            val group = groupService.findByGroupName(groupName) ?: groupService.create(groupName)
            val user = userService.create(username, group)
            group.addUser(user)
            logJoiningToGroup(user, group)
            launch {
                log.info("Running new message receiver for user ${user.name} of group ${group.name}")
                runReceiver(inputStream, user, group)
            }
            launch {
                log.info("Running new message sender for user ${user.name} of group ${group.name}")
                runSender(outputStream, user)
            }
        } catch (e: SocketException) {
            log.error("Failed to connect to server", e)
        }
    }

    private suspend fun getUserAuthorities(inputStream: InputStream): Authorities {
        val reader = inputStream.bufferedReader()
        val userName = withContext(Dispatchers.IO) {
            reader.readLine()
        }
        val groupName = withContext(Dispatchers.IO) {
            reader.readLine()
        }
        return Authorities(userName, groupName)
    }

    private suspend fun logJoiningToGroup(user: User, group: Group) {
        val text = "${user.name} добавился в группу ${group.name}"
        val message = Message(text)
        sendMessageToGroup(group, message)
    }

    private suspend fun runReceiver(inputStream: InputStream, user: User, group: Group): Unit =
        channelFlow<String> {
            val reader = inputStream.bufferedReader()
            while (true) {
                send(reader.readLine())
            }
        }
            .catch {
                log.error("Error during receiving message", it)
                user.stopMessaging()
            }
            .flowOn(Dispatchers.IO)
            .onCompletion { log.info("Completed receiving messages from user ${user.name} in group ${group.name}") }
            .collect {
                val message = Message(user.name, it)
                sendMessageToGroup(group, message)
            }

    suspend fun sendMessageToGroup(group: Group, message: Message) =
        group.getUsers().asFlow()
            .collect {
                it.sendMessage(message)
            }

    private suspend fun runSender(outputStream: OutputStream, user: User) {
        val writer = outputStream.bufferedWriter()
        user.messages()
            .onCompletion { log.info("Completed sending message to user ${user.name}") }
            .onEach {
                writer.append(it.toString())
                writer.newLine()
                writer.flush()
            }
            .flowOn(Dispatchers.IO)
            .collect {}
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(ChatServer::class.java)
    }

    private data class Authorities(val username: String, val groupName: String)
}
