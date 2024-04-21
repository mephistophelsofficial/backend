package com.angoga.kfd_workshop_server.service.impl.auth

import com.angoga.kfd_workshop_server.database.entity.User
import com.angoga.kfd_workshop_server.errors.ApiError
import com.angoga.kfd_workshop_server.mappers.UserMapper
import com.angoga.kfd_workshop_server.model.request.LoginRequest
import com.angoga.kfd_workshop_server.model.request.RegistrationRequest
import com.angoga.kfd_workshop_server.model.response.LoginResponse
import com.angoga.kfd_workshop_server.model.response.user.UserResponse
import com.angoga.kfd_workshop_server.service.AuthService
import com.angoga.kfd_workshop_server.service.UserService
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.Modifying
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val jwtHelper: JwtHelper,
    private val userService: UserService,
    private val mapper: UserMapper,
    private val encoder: PasswordEncoder
) : AuthService {

    @Transactional
    override fun login(request: LoginRequest): LoginResponse {
        val user = loginUser(request)
        val jwt = jwtHelper.generateAccessToken(user)
        return LoginResponse(jwt)
    }

    @Modifying
    @Transactional
    override fun register(request: RegistrationRequest): LoginResponse {
        val user = userService.createUser(request)
        return LoginResponse(jwtHelper.generateAccessToken(user))
    }

    private fun loginUser(request: LoginRequest): User {
        val user = userService.findEntityByEmail(request.email)
        if (!encoder.matches(request.password, user.hash)) {
            throw ApiError(HttpStatus.UNAUTHORIZED, "Неправильный пароль")
        }
        return user
    }



}