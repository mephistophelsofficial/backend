package com.angoga.kfd_workshop_server.service

import com.angoga.kfd_workshop_server.database.entity.User
import com.angoga.kfd_workshop_server.model.request.RegistrationRequest
import com.angoga.kfd_workshop_server.model.response.user.UserResponse

interface UserService {
    fun createUser(request: RegistrationRequest): User
    fun existByEmail(email: String): Boolean
    fun findEntityByEmail(email: String): User
    fun findEntityById(id: Long): User
    fun getSelfProfile(userId: Long): UserResponse
    fun getUserProfile(userId: Long): UserResponse
}