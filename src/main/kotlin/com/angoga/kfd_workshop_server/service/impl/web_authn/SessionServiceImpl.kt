package com.angoga.kfd_workshop_server.service.impl.web_authn

import com.angoga.kfd_workshop_server.database.repository.KeyDao
import com.angoga.kfd_workshop_server.database.repository.SessionDao
import com.angoga.kfd_workshop_server.errors.ResourceNotFoundException
import com.angoga.kfd_workshop_server.model.response.web_authn.ChallengeResponse
import com.angoga.kfd_workshop_server.service.SessionService
import com.angoga.kfd_workshop_server.service.UserService
import com.angoga.kfd_workshop_server.util.getPrincipal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SessionServiceImpl(
    @Autowired
    private val userService: UserService,
    @Autowired
    private val sessionRepo: SessionDao,
    @Autowired
    private val keyRepo: KeyDao,
) : SessionService {
    override fun getSessionChallenge(sessionId: Long): ChallengeResponse = ChallengeResponse(
        CryptoService.code(
            sessionRepo.findById(sessionId).orElseThrow { ResourceNotFoundException() }.challenge,
            publicKeyAsString = keyRepo.findByUser(userService.findEntityById(getPrincipal()))!!.publicKey
        )
    )
}