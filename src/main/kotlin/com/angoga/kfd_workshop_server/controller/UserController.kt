package com.angoga.kfd_workshop_server.controller

import com.angoga.kfd_workshop_server.model.response.user.UserResponse
import com.angoga.kfd_workshop_server.service.UserService
import com.angoga.kfd_workshop_server.util.API_VERSION_1
import com.angoga.kfd_workshop_server.util.getPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("$API_VERSION_1/user")
class UserController(
    private val service: UserService
) {

    @GetMapping("/me")
    fun getSelfProfile(): UserResponse {
        return service.getSelfProfile(getPrincipal())
    }

    @GetMapping("/{userId}")
    fun getUserProfile(@PathVariable userId: Long): UserResponse {
        return service.getUserProfile(userId)
    }
}