package com.alex.chat.server.service

import com.alex.chat.server.models.User

interface GroupService {
    fun addUserToGroup(userName: String, groupName: String): User
}