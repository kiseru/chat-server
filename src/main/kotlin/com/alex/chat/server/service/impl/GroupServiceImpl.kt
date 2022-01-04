package com.alex.chat.server.service.impl

import com.alex.chat.server.models.Group
import com.alex.chat.server.models.User
import com.alex.chat.server.service.GroupService
import com.alex.chat.server.service.UserService
import java.util.concurrent.ConcurrentHashMap

class GroupServiceImpl(
    private val userService: UserService,
) : GroupService {
    private val groups = ConcurrentHashMap<String, Group>()

    override fun addUserToGroup(userName: String, groupName: String): User {
        val group = groups.computeIfAbsent(groupName) { Group(it) }
        val user = userService.create(userName, group)
        group.addUser(user)
        return user
    }
}