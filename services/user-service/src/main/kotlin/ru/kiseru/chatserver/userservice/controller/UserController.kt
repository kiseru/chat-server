package ru.kiseru.chatserver.userservice.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.kiseru.chatserver.shared.dto.UserRequestDto
import ru.kiseru.chatserver.shared.dto.UserResponseDto
import ru.kiseru.chatserver.userservice.service.UserService

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService,
) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun register(userDto: UserRequestDto): UserResponseDto {
        return userService.register(userDto)
    }
}