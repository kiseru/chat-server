package com.alex.chat.server.model

class Group(
    private val name: String,
) {
    private val users = mutableSetOf<User>()

    fun addUser(user: User) {
        synchronized(users) {
            users.add(user)
            user.logJoiningToGroup(name)
        }
    }

    fun sendMessage(message: Message) {
        for (user in users) {
            user.sendMessage(message)
        }
    }
}