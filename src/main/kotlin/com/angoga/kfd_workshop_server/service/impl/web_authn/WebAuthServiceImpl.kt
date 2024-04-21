package com.angoga.kfd_workshop_server.service.impl.web_authn

import com.angoga.kfd_workshop_server.database.entity.Key
import com.angoga.kfd_workshop_server.database.entity.Session
import com.angoga.kfd_workshop_server.database.repository.KeyDao
import com.angoga.kfd_workshop_server.database.repository.SessionDao
import com.angoga.kfd_workshop_server.errors.AlreadyExistsException
import com.angoga.kfd_workshop_server.errors.ApiError
import com.angoga.kfd_workshop_server.errors.ResourceNotFoundException
import com.angoga.kfd_workshop_server.errors.WebAuthnNotEnabledException
import com.angoga.kfd_workshop_server.model.request.web_authn.WebAuthGrantAccessRequest
import com.angoga.kfd_workshop_server.model.request.web_authn.WebAuthLoginRequest
import com.angoga.kfd_workshop_server.model.request.web_authn.WebAuthRegistrationRequest
import com.angoga.kfd_workshop_server.model.response.*
import com.angoga.kfd_workshop_server.model.response.web_authn.GrantedAccessResponse
import com.angoga.kfd_workshop_server.model.response.web_authn.WebAuthLoginResponse
import com.angoga.kfd_workshop_server.service.UserService
import com.angoga.kfd_workshop_server.service.WebAuthService
import com.angoga.kfd_workshop_server.service.impl.auth.JwtHelper
import com.angoga.kfd_workshop_server.util.enums.SessionState
import com.angoga.kfd_workshop_server.util.getPrincipal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
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
    @Value("\${web_authn.session.lifetime}")
    private val sessionLifetime: Long = 60 //in seconds
) : WebAuthService {

    @Transactional
    override fun register(request: WebAuthRegistrationRequest): MessageResponse {
        if(keyRepo.findByUser(userService.findEntityById(getPrincipal())) != null){
            throw AlreadyExistsException()
        }
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
        if(keyRepo.findByUser(userService.findEntityByEmail(request.email)) == null) {
            throw WebAuthnNotEnabledException()
        }
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        val challenge = (1..32)
            .map { chars.random() }
            .joinToString("")

        println("Web-Auth-login (Соль): $challenge")

        val session = sessionRepo.save(Session(user = userService.findEntityByEmail(request.email), challenge = challenge, sessionState = SessionState.OPEN))
        val token = jwtHelper.generateWaitingAccessToken(session.id)
        return WebAuthLoginResponse(
            session.id.toString(),
            CryptoService.code(
                challenge = challenge,
                publicKeyAsString = keyRepo.findByUser(userService.findEntityByEmail(request.email))!!.publicKey
            ),
            token
        )
    }

    @Transactional
    override fun grantAccess(request: WebAuthGrantAccessRequest): MessageResponse {
        println("Web-Auth-grantAccess (Попытка расшифровки): ${request.solvedChallenge}")
        val session = sessionRepo.findById(request.sessionId.toLong()).orElseThrow { ResourceNotFoundException() }
        if (session.challenge != request.solvedChallenge) {
            throw ApiError("Bad challenge")
        }
        if (session.sessionState == SessionState.OPEN) {
            session.sessionState = SessionState.CLOSED
        }
        if (session.sessionState == SessionState.EXPIRED) {
            throw ApiError(message = "Session expired!", status = HttpStatus.EXPECTATION_FAILED)
        }
        sessionRepo.save(session)
        return MessageResponse("Access granted")
    }

    @Transactional
    override fun checkAccess(): GrantedAccessResponse {
        lateinit var response: GrantedAccessResponse
        val session = sessionRepo.findById(getPrincipal()).orElseThrow { ResourceNotFoundException() }
        if (session.createdAt.isBefore(LocalDateTime.now().minusSeconds(sessionLifetime)))
            when (session.sessionState) {
                SessionState.OPEN -> throw ApiError(message = "Session not confirmed", status = HttpStatus.UNAUTHORIZED)
                SessionState.CLOSED -> response =
                    GrantedAccessResponse(accessJwt = jwtHelper.generateAccessToken(sessionRepo.findById(
                        getPrincipal()
                    ).orElseThrow { ResourceNotFoundException() }.user
                    ),
                        privateKey = keyRepo.findByUser(
                            sessionRepo.findById(getPrincipal()).orElseThrow { ResourceNotFoundException() }.user
                        )!!.privateKey
                    )

                SessionState.EXPIRED -> throw ApiError(message = "Session expired!", status = HttpStatus.EXPECTATION_FAILED)
            }
        return response
    }
}