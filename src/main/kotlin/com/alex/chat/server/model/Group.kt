package com.alex.chat.server.model

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class Group(
    val name: String,
) {

    private val mutex = Mutex()
    private val users = mutableListOf<User>()

    suspend fun addUser(user: User) =
        mutex.withLock {
            users.add(user)
        }

    suspend fun getUsers(): List<User> =
        mutex.withLock { users.toList() }
}
