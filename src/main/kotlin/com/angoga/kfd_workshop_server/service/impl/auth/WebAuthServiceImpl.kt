package com.angoga.kfd_workshop_server.service.impl.auth

import com.angoga.kfd_workshop_server.database.entity.Key
import com.angoga.kfd_workshop_server.database.repository.KeyDao
import com.angoga.kfd_workshop_server.database.repository.SessionDao
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
        return MessageResponse()
    }

    override fun getFingerprints(email: String): FingerprintResponse = FingerprintResponse(keyRepo.findAllByUser(userService.findEntityByEmail(email)).map { it.fingerprint })

    override fun login(request: WebAuthLoginRequest): WebAuthLoginResponse {

    }

    override fun grantAccess(request: WebAuthGrantAccessRequest): MessageResponse {
        TODO("Not yet implemented")
    }

    override fun checkAccess(): GrantedAccessResponse {
        TODO("Not yet implemented")
    }
}