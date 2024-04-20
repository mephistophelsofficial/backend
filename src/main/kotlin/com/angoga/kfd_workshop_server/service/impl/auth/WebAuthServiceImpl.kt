package com.angoga.kfd_workshop_server.service.impl.auth

import com.angoga.kfd_workshop_server.database.entity.Key
import com.angoga.kfd_workshop_server.database.entity.Session
import com.angoga.kfd_workshop_server.database.entity.State
import com.angoga.kfd_workshop_server.database.repository.KeyDao
import com.angoga.kfd_workshop_server.database.repository.SessionDao
import com.angoga.kfd_workshop_server.errors.ApiError
import com.angoga.kfd_workshop_server.errors.ResourceNotFoundException
import com.angoga.kfd_workshop_server.errors.SessionExpiredException
import com.angoga.kfd_workshop_server.model.request.WebAuthGrantAccessRequest
import com.angoga.kfd_workshop_server.model.request.WebAuthLoginRequest
import com.angoga.kfd_workshop_server.model.request.WebAuthRegistrationRequest
import com.angoga.kfd_workshop_server.model.response.*
import com.angoga.kfd_workshop_server.service.UserService
import com.angoga.kfd_workshop_server.service.WebAuthService
import com.angoga.kfd_workshop_server.util.getPrincipal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class WebAuthServiceImpl(
    @Autowired
    private val sessionRepo: SessionDao,
    @Autowired
    private val keyRepo: KeyDao,
    @Autowired
    private val userService: UserService,
    @Autowired
    private val jwtHelper: JwtHelper
    ) : WebAuthService {
    override fun register(request: WebAuthRegistrationRequest): MessageResponse {
        keyRepo.save(Key(publicKey = request.publicKey, privateKey = request.privateKey, user = userService.findEntityById(getPrincipal())))
        return MessageResponse("Successful WebAuthn registration!")
    }

    @Deprecated("Misconception idea")
    override fun getFingerprints(email: String): FingerprintResponse {
        TODO("Для лучших времен")
        //return FingerprintResponse(keyRepo.findByUser(userService.findEntityByEmail(email)).map { it.fingerprint })
    }

    override fun login(request: WebAuthLoginRequest): WebAuthLoginResponse {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        val challenge = (1..32)
            .map { chars.random() }
            .joinToString("")
        val session = Session(user = userService.findEntityByEmail(request.email), challenge =  challenge, state = State.OPEN)
        val token = jwtHelper.generateWaitingAccessToken(session.id)
        return WebAuthLoginResponse(session.id.toString(), CryptoService.code(challenge = challenge, publicKeyAsString = keyRepo.findByUser(userService.findEntityByEmail(request.email)).publicKey), token)
    }

    override fun grantAccess(request: WebAuthGrantAccessRequest): MessageResponse {
        val session = sessionRepo.findById(request.sessionId.toLong()).orElseThrow { ResourceNotFoundException() }
        if(session.challenge != request.solvedChallenge) {
            throw ApiError("Bad challenge")
        }
        if(session.state == State.OPEN){
            session.state = State.CLOSED
        }
        if(session.state == State.EXPIRED){
            throw SessionExpiredException()
        }
        sessionRepo.save(session)
        return MessageResponse("Access granted")
    }

    override fun checkAccess(): GrantedAccessResponse {
        lateinit var response : GrantedAccessResponse
        when(sessionRepo.findById(getPrincipal()).orElseThrow { ResourceNotFoundException() }.state){
            State.OPEN -> throw ApiError(message = "Кринжуешь братик", status = HttpStatus.UNAUTHORIZED)
            State.CLOSED -> response = GrantedAccessResponse(accessJwt = jwtHelper.generateAccessToken(sessionRepo.findById(
                getPrincipal()
            ).orElseThrow{ResourceNotFoundException()}.user), privateKey = keyRepo.findByUser(sessionRepo.findById(getPrincipal()).orElseThrow { ResourceNotFoundException() }.user).privateKey)
            State.EXPIRED -> throw SessionExpiredException()
        }
        return response
    }

    override fun getKeys(): KeysResponse {
        val key = userService.findEntityById(getPrincipal()).key ?: throw ResourceNotFoundException()
        return KeysResponse(public = key.publicKey, private = key.privateKey)
    }
}