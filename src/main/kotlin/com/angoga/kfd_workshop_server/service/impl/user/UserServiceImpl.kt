package com.angoga.kfd_workshop_server.service.impl.user

import com.angoga.kfd_workshop_server.database.entity.User
import com.angoga.kfd_workshop_server.database.repository.UserDao
import com.angoga.kfd_workshop_server.errors.AlreadyExistsException
import com.angoga.kfd_workshop_server.errors.ResourceNotFoundException
import com.angoga.kfd_workshop_server.mappers.UserMapper
import com.angoga.kfd_workshop_server.model.request.RegistrationRequest
import com.angoga.kfd_workshop_server.model.response.user.UserResponse
import com.angoga.kfd_workshop_server.service.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val passwordEncoder: PasswordEncoder,
    private val mapper: UserMapper,
    private val dao: UserDao,
) : UserService {

    override fun existByEmail(email: String): Boolean {
        return dao.existsByEmail(email)
    }

    override fun findEntityByEmail(email: String) : User {
        return dao.findByEmail(email).orElseThrow { ResourceNotFoundException(email) }
    }

    override fun findEntityById(id: Long): User {
        return dao.findById(id).orElseThrow { ResourceNotFoundException(id) }
    }

    override fun getSelfProfile(userId: Long): UserResponse {
        val user = findEntityById(userId)
        return mapper.asResponse(user)
    }

    override fun getUserProfile(userId: Long): UserResponse {
        val user = findEntityById(userId)
        return mapper.asResponse(user)
    }

    override fun createUser(request: RegistrationRequest): User {
        if (dao.existsByEmail(request.email)) {
            throw AlreadyExistsException(request.email)
        }
        val user = mapper.asEntity(request).apply {
            hash = passwordEncoder.encode(request.password)
            dao.save(this)
        }
        return user
    }

}