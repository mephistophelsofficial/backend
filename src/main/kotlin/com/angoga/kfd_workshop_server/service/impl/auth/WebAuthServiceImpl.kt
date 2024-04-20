package com.angoga.kfd_workshop_server.service.impl.auth

import com.angoga.kfd_workshop_server.database.entity.Key
import com.angoga.kfd_workshop_server.database.entity.State
import com.angoga.kfd_workshop_server.database.repository.KeyDao
import com.angoga.kfd_workshop_server.database.repository.SessionDao
import com.angoga.kfd_workshop_server.errors.ResourceNotFoundException
import com.angoga.kfd_workshop_server.errors.SessionExpiredException
import com.angoga.kfd_workshop_server.model.request.WebAuthGrantAccessRequest
import com.angoga.kfd_workshop_server.model.request.WebAuthLoginRequest
import com.angoga.kfd_workshop_server.model.request.WebAuthRegistrationRequest
import com.angoga.kfd_workshop_server.model.response.FingerprintResponse
import com.angoga.kfd_workshop_server.model.response.GrantedAccessResponse
import com.angoga.kfd_workshop_server.model.response.MessageResponse
import com.angoga.kfd_workshop_server.model.response.WebAuthLoginResponse
import com.angoga.kfd_workshop_server.service.UserService
import com.angoga.kfd_workshop_server.service.WebAuthService
import com.angoga.kfd_workshop_server.util.getPrincipal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.net.http.HttpResponse

@Service
class WebAuthServiceImpl(
    @Autowired
    private val sessionRepo: SessionDao,
    @Autowired
    private val keyRepo: KeyDao,
    @Autowired
    private val userService: UserService,
    ) : WebAuthService {
    override fun register(request: WebAuthRegistrationRequest): MessageResponse {
        keyRepo.save(Key(key = request.key, fingerprint = request.fingerPrint, userService.findEntityById(getPrincipal())))
        return MessageResponse("Successful WebAuthn registration!")
    }

    override fun getFingerprints(email: String): FingerprintResponse = FingerprintResponse(keyRepo.findAllByUser(userService.findEntityByEmail(email)).map { it.fingerprint })

    override fun login(request: WebAuthLoginRequest): WebAuthLoginResponse {
        TODO()
    }

    override fun grantAccess(request: WebAuthGrantAccessRequest): MessageResponse {
        val session = sessionRepo.findById(request.sessionId.toLong()).orElseThrow { ResourceNotFoundException() }
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
        TODO()
//        if ()
    }
}