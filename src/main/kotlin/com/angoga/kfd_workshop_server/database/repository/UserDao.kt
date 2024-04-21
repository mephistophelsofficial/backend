package com.angoga.kfd_workshop_server.database.repository

import com.angoga.kfd_workshop_server.database.entity.User
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserDao : AppRepository<User> {
    fun findByEmail(email: String): Optional<User>

    fun existsByEmail(email: String): Boolean
}