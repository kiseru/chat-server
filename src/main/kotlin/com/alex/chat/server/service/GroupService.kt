package com.alex.chat.server.service

import com.alex.chat.server.model.Group

interface GroupService {

    suspend fun create(groupName: String): Group

    suspend fun findByGroupName(groupName: String): Group?
}
