package com.alex.chat.server.service

import com.alex.chat.server.models.Group

interface GroupService {
    fun create(name: String): Group
}