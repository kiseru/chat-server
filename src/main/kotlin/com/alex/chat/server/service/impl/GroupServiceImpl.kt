package com.alex.chat.server.service.impl

import com.alex.chat.server.model.Group
import com.alex.chat.server.model.User
import com.alex.chat.server.service.GroupService
import com.alex.chat.server.service.UserService
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class GroupServiceImpl @Autowired constructor(
    private val userService: UserService,
) : GroupService {

    private val mutex = Mutex()
    private val groups = mutableMapOf<String, Group>()

    override suspend fun addUserToGroup(userName: String, groupName: String): User {
        val group = mutex.withLock {
            groups.getOrPut(groupName) { Group(groupName) }
        }
        val user = userService.create(userName, group)
        group.addUser(user)
        return user
    }
}
