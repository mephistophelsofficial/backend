package com.angoga.kfd_workshop_server.service

import com.angoga.kfd_workshop_server.model.request.LoginRequest
import com.angoga.kfd_workshop_server.model.request.RegistrationRequest
import com.angoga.kfd_workshop_server.model.response.LoginResponse

interface AuthService {
    fun login(request: LoginRequest): LoginResponse
    fun register(request: RegistrationRequest): LoginResponse
}