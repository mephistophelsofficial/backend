package com.angoga.kfd_workshop_server.database.repository

import com.angoga.kfd_workshop_server.database.entity.Like
import com.angoga.kfd_workshop_server.database.entity.Publication
import com.angoga.kfd_workshop_server.database.entity.User
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.Optional

@Repository
interface LikeDao : AppRepository<Like> {
    fun existsByUserIdAndPublication(userId: Long, publication: Publication): Boolean
    fun findByUserAndPublication(user: User, publication: Publication): Optional<Like>
    fun countByPublication(publication: Publication): Int
}