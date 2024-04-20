package com.angoga.kfd_workshop_server.mappers

import com.angoga.kfd_workshop_server.database.entity.User
import com.angoga.kfd_workshop_server.model.request.RegistrationRequest
import com.angoga.kfd_workshop_server.model.response.user.UserResponse
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component


@Component
class UserMapper {
    fun asEntity(request: RegistrationRequest): User {
        return User(
            email = request.email,
            name = request.name
        )
    }
    fun asNullableResponse(entity: User?): UserResponse? {
        return if (entity == null) null else asResponse(entity)
    }

    fun asResponse(entity: User): UserResponse {
        return UserResponse(
            id = entity.id,
            email = entity.email,
            name = entity.name
        )
    }
}