package ru.kiseru.chatserver.userservice.service.mapper

import org.mapstruct.Mapper
import ru.kiseru.chatserver.shared.dto.UserRequestDto
import ru.kiseru.chatserver.shared.dto.UserResponseDto
import ru.kiseru.chatserver.userservice.model.User

@Mapper
interface UserMapper {

    fun toModel(userDto: UserRequestDto): User

    fun toResponseDto(user: User): UserResponseDto
}