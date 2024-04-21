package com.angoga.kfd_workshop_server.security

import com.angoga.kfd_workshop_server.errors.ApiError
import com.angoga.kfd_workshop_server.errors.CorruptedTokenException
import com.angoga.kfd_workshop_server.security.model.UserPrincipal
import com.angoga.kfd_workshop_server.util.WAITING_ROLE
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import io.jsonwebtoken.*
import java.util.*
import javax.crypto.SecretKey
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.GrantedAuthority

@Component
class JwtParser(
    @Value("\${spring.security.jwt.secret:#{null}}")
    secret: String?,
) {
    val secret: SecretKey by lazy {
        if (secret == null) {
            throw NullPointerException("spring.security.jwt.secret is not provided in configuration")
        }
        Keys.hmacShaKeyFor(secret.toByteArray())
    }
    private val headerPrefix = "Bearer "
    private val algorithm = SignatureAlgorithm.HS256


    fun parseToken(token: String): Jws<Claims> {
        return try {
            Jwts.parserBuilder().setSigningKey(this.secret).build().parseClaimsJws(token)
        } catch (e: ExpiredJwtException) {
            throw ApiError(HttpStatus.UNAUTHORIZED, "Авторизуйтесь заново", exception = e)
        } catch (e: JwtException) {
            throw CorruptedTokenException()
        }
    }

    fun createToken(vararg claims: Pair<String, Any>, expiration: Date? = null): String {
        val token = Jwts.builder()
            .addClaims(claims.associate { it })
            .signWith(secret, algorithm)
        if (expiration != null) {
            token.setExpiration(expiration)
        }
        return token.compact()
    }

    fun parseTokenPrincipalFromHeader(tokenFromHeader: String): UserPrincipal {
        val token = tokenFromHeader.replace(headerPrefix, "")
        val claims = parseToken(token)
        val userId = claims.body.get("userId", Integer::class.java)?.toLong() ?: throw CorruptedTokenException()
        val authorities = claims.body.get("authorities", MutableList::class.java)
        println(userId)
        return UserPrincipal(userId, listOf(GrantedAuthority { authorities.first().toString() }))
    }

    fun createAuthToken(header: String): UserPrincipal {
        if (!header.startsWith(headerPrefix)) throw CorruptedTokenException()
        val principal = parseTokenPrincipalFromHeader(header)
        return UserPrincipal(principal.userId)
    }

    fun createWaitingAuthToken(header: String): UserPrincipal {
        if (!header.startsWith(headerPrefix)) throw CorruptedTokenException()
        val principal = parseTokenPrincipalFromHeader(header)
        return UserPrincipal(principal.userId, mutableListOf(GrantedAuthority { WAITING_ROLE }))
    }
}
