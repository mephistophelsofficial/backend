package com.angoga.kfd_workshop_server.service.impl.auth

import com.angoga.kfd_workshop_server.database.entity.User
import com.angoga.kfd_workshop_server.logging.FreelancingLogger
import com.angoga.kfd_workshop_server.security.model.Authority
import com.angoga.kfd_workshop_server.security.JwtParser
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class JwtHelper(
    private val jwt: JwtParser,

    @Value("\${spring.security.jwt.access.lifetime}")
    private val accessTokenLifetime: Long,
) {

    fun generateAccessToken(user: User): String {
        val authorities = mutableListOf(Authority.USER)
        return jwt.createToken(
            "userId" to user.id,
            "permissions" to emptyList<String>(),
            "authorities" to authorities,
            expiration = getAccessTokenExpiration(),
        )
    }

    fun generateWaitingAccessToken(id: Long): String {
        val authorities = mutableListOf(Authority.WAITING)
        val logger = FreelancingLogger(this.javaClass)
        logger.info(authorities[0].authority.authority)
        logger.info("$id")
        return jwt.createToken(
            "userId" to id,
            "permissions" to emptyList<String>(),
            "authorities" to authorities,
            expiration = getAccessTokenExpiration(),
        )
    }


    private fun getAccessTokenExpiration(): Date =
        Instant.now().plus(accessTokenLifetime, ChronoUnit.DAYS).let { Date.from(it) }

}