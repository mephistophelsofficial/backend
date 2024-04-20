package com.angoga.kfd_workshop_server.service

import com.angoga.kfd_workshop_server.model.request.WebAuthGrantAccessRequest
import com.angoga.kfd_workshop_server.model.request.WebAuthLoginRequest
import com.angoga.kfd_workshop_server.model.request.WebAuthRegistrationRequest
import com.angoga.kfd_workshop_server.model.response.*
import java.net.http.HttpResponse

interface WebAuthService {

    fun register(request: WebAuthRegistrationRequest) : MessageResponse

    @Deprecated("Misconception idea")
    fun getFingerprints(email: String) : FingerprintResponse

    fun login(request: WebAuthLoginRequest) : WebAuthLoginResponse

    fun grantAccess(request: WebAuthGrantAccessRequest) : MessageResponse

    fun checkAccess() : GrantedAccessResponse

    fun getKeys() : KeysResponse

    fun getSessionChallenge(sessionId: Long) : ChallengeResponse
}