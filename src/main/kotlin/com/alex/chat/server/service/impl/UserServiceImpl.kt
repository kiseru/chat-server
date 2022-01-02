package com.alex.chat.server.service.impl

import com.alex.chat.server.models.Group
import com.alex.chat.server.models.User
import com.alex.chat.server.service.UserService

class UserServiceImpl : UserService {
    override fun create(name: String, group: Group) = User(name, group)
}