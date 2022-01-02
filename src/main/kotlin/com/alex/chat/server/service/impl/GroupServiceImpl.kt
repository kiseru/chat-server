package com.alex.chat.server.service.impl

import com.alex.chat.server.models.Group
import com.alex.chat.server.service.GroupService

class GroupServiceImpl : GroupService {
    override fun create(name: String) = Group(name)
}