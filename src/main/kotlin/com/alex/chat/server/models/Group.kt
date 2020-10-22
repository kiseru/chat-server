package com.alex.chat.server.models

class Group(val name: String) {
    private val users = HashSet<User>()

    fun addUser(user: User) {
        val messageText: String
        synchronized(users) {
            users.add(user)
            messageText = "$user joined the group"
            sendMessageFromServer(messageText)
        }
        println(messageText)
    }

    fun sendMessageFromServer(text: String) {
        val message = Message(text)
        sendMessage(message)
    }

    fun sendMessage(message: Message) {
        users.forEach { it.sendMessage(message) }
    }
}