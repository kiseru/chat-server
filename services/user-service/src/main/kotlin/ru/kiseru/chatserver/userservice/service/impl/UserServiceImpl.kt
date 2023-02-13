package ru.kiseru.chatserver.userservice.service.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import ru.kiseru.chatserver.shared.dto.UserRequestDto
import ru.kiseru.chatserver.shared.dto.UserResponseDto
import ru.kiseru.chatserver.userservice.model.User
import ru.kiseru.chatserver.userservice.service.UserService
import ru.kiseru.chatserver.userservice.service.mapper.UserMapper
import java.util.concurrent.ConcurrentHashMap

@Service
class UserServiceImpl(
    private val userMapper: UserMapper,
) : UserService {

    private val userStore: MutableMap<String, User> = ConcurrentHashMap<String, User>()

    override suspend fun register(userDto: UserRequestDto): UserResponseDto {
        val user = userMapper.toModel(userDto)
        withContext(Dispatchers.Default) {
            userStore[user.username] = user
        }
        return userMapper.toResponseDto(user)
    }
}