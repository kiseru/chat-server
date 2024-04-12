package com.alex.chat.server.service.impl

import com.alex.chat.server.model.Group
import com.alex.chat.server.service.GroupService
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.springframework.stereotype.Component

@Component
class GroupServiceImpl : GroupService {

    private val mutex = Mutex()
    private val groups = mutableMapOf<String, Group>()

    override suspend fun create(groupName: String): Group =
        mutex.withLock {
            val group = Group(groupName)
            groups[groupName] = group
            group
        }

    override suspend fun findByGroupName(groupName: String): Group? =
        mutex.withLock { groups[groupName] }
}
