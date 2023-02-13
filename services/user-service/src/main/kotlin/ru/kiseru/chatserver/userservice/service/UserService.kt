package ru.kiseru.chatserver.userservice.service

import ru.kiseru.chatserver.shared.dto.UserRequestDto
import ru.kiseru.chatserver.shared.dto.UserResponseDto

interface UserService {

    suspend fun register(userDto: UserRequestDto): UserResponseDto
}