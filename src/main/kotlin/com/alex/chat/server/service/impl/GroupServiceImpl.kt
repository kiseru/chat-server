package com.alex.chat.server.service.impl

import com.alex.chat.server.models.Group
import com.alex.chat.server.service.GroupService
import java.util.concurrent.ConcurrentHashMap

class GroupServiceImpl : GroupService {
    private val groups = ConcurrentHashMap<String, Group>()

    override fun findOrCreate(name: String): Group {
        return groups.computeIfAbsent(name) { Group(it) }
    }
}