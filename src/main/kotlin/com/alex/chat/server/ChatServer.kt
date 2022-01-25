package com.alex.chat.server

import com.alex.chat.server.model.User
import com.alex.chat.server.service.GroupService
import com.alex.chat.server.service.ReceiverService
import com.alex.chat.server.service.SenderService
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.Executors

@Component
class ChatServer(
    private val groupService: GroupService,
    private val receiverService: ReceiverService,
    private val senderService: SenderService,
) {

    private val executor = Executors.newCachedThreadPool()

    fun run() {
        val server = ServerSocket(5003)
        while (!server.isClosed) {
            val socket = server.accept()
            executor.execute { handleConnection(socket) }
        }
    }

    private fun handleConnection(socket: Socket) {
        val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
        val writer = PrintWriter(socket.getOutputStream())

        val user = authorizeUser(reader)
        executor.execute(receiverService.createReceiver(reader, user))
        executor.execute(senderService.createSender(writer, user))
    }

    private fun authorizeUser(reader: BufferedReader): User {
        val userName = reader.readLine()
        val groupName = reader.readLine()
        return groupService.addUserToGroup(userName, groupName)
    }
}
