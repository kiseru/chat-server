package com.alex.chat.server.service

import com.alex.chat.server.models.Group
import com.alex.chat.server.models.User

interface UserService {
    fun create(name: String, group: Group): User
}