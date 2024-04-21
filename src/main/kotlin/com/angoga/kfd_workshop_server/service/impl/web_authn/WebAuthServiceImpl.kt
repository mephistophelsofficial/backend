package com.angoga.kfd_workshop_server.service.impl.web_authn

import com.angoga.kfd_workshop_server.database.entity.Key
import com.angoga.kfd_workshop_server.database.entity.Session
import com.angoga.kfd_workshop_server.database.entity.State
import com.angoga.kfd_workshop_server.database.repository.KeyDao
import com.angoga.kfd_workshop_server.database.repository.SessionDao
import com.angoga.kfd_workshop_server.errors.ApiError
import com.angoga.kfd_workshop_server.errors.ResourceNotFoundException
import com.angoga.kfd_workshop_server.model.request.WebAuthGrantAccessRequest
import com.angoga.kfd_workshop_server.model.request.WebAuthLoginRequest
import com.angoga.kfd_workshop_server.model.request.WebAuthRegistrationRequest
import com.angoga.kfd_workshop_server.model.response.*
import com.angoga.kfd_workshop_server.service.UserService
import com.angoga.kfd_workshop_server.service.WebAuthService
import com.angoga.kfd_workshop_server.service.impl.auth.JwtHelper
import com.angoga.kfd_workshop_server.util.getPrincipal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class WebAuthServiceImpl(
    @Autowired
    private val sessionRepo: SessionDao,
    @Autowired
    private val keyRepo: KeyDao,
    @Autowired
    private val userService: UserService,
    @Autowired
    private val jwtHelper: JwtHelper,
) : WebAuthService {

    val SESSION_LIFETIME : Long = 60 //in seconds
    override fun register(request: WebAuthRegistrationRequest): MessageResponse {
        keyRepo.save(
            Key(
                publicKey = request.publicKey,
                privateKey = request.privateKey,
                user = userService.findEntityById(getPrincipal())
            )
        )
        return MessageResponse("Successful WebAuthn registration!")
    }

    @Transactional
    override fun login(request: WebAuthLoginRequest): WebAuthLoginResponse {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        val challenge = (1..32)
            .map { chars.random() }
            .joinToString("")

        val session = sessionRepo.save(Session(user = userService.findEntityByEmail(request.email), challenge = challenge, state = State.OPEN))
        val token = jwtHelper.generateWaitingAccessToken(session.id)
        return WebAuthLoginResponse(
            session.id.toString(),
            CryptoService.code(
                challenge = challenge,
                publicKeyAsString = keyRepo.findByUser(userService.findEntityByEmail(request.email)).publicKey
            ),
            token
        )
    }

    override fun grantAccess(request: WebAuthGrantAccessRequest): MessageResponse {
        val session = sessionRepo.findById(request.sessionId.toLong()).orElseThrow { ResourceNotFoundException() }
        if (session.challenge != request.solvedChallenge) {
            throw ApiError("Bad challenge")
        }
        if (session.state == State.OPEN) {
            session.state = State.CLOSED
        }
        if (session.state == State.EXPIRED) {
            throw ApiError(message = "Session expired!", status = HttpStatus.EXPECTATION_FAILED)
        }
        sessionRepo.save(session)
        return MessageResponse("Access granted")
    }

    override fun checkAccess(): GrantedAccessResponse {
        lateinit var response: GrantedAccessResponse
        val session = sessionRepo.findById(getPrincipal()).orElseThrow { ResourceNotFoundException() }
        if (session.createdAt.isBefore(LocalDateTime.now().minusSeconds(SESSION_LIFETIME)))
            when (session.state) {
                State.OPEN -> throw ApiError(message = "Session not confirmed", status = HttpStatus.UNAUTHORIZED)
                State.CLOSED -> response =
                    GrantedAccessResponse(accessJwt = jwtHelper.generateAccessToken(sessionRepo.findById(
                        getPrincipal()
                    ).orElseThrow { ResourceNotFoundException() }.user
                    ),
                        privateKey = keyRepo.findByUser(
                            sessionRepo.findById(getPrincipal()).orElseThrow { ResourceNotFoundException() }.user
                        ).privateKey
                    )

                State.EXPIRED -> throw ApiError(message = "Session expired!", status = HttpStatus.EXPECTATION_FAILED)
            }
        return response
    }
}