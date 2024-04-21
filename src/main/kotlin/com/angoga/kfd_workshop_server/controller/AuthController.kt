package com.angoga.kfd_workshop_server.controller

import com.angoga.kfd_workshop_server.model.request.LoginRequest
import com.angoga.kfd_workshop_server.model.request.RegistrationRequest
import com.angoga.kfd_workshop_server.model.response.LoginResponse
import com.angoga.kfd_workshop_server.model.response.user.UserResponse
import com.angoga.kfd_workshop_server.service.AuthService
import com.angoga.kfd_workshop_server.util.API_PUBLIC
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("$API_PUBLIC/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): LoginResponse {
        return authService.login(request)
    }

    @PostMapping("/registration")
    fun register(@Valid @RequestBody request: RegistrationRequest): LoginResponse {
        return authService.register(request)
    }


}