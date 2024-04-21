package com.angoga.kfd_workshop_server.service

import com.angoga.kfd_workshop_server.model.request.web_authn.WebAuthGrantAccessRequest
import com.angoga.kfd_workshop_server.model.request.web_authn.WebAuthLoginRequest
import com.angoga.kfd_workshop_server.model.request.web_authn.WebAuthRegistrationRequest
import com.angoga.kfd_workshop_server.model.response.*
import com.angoga.kfd_workshop_server.model.response.web_authn.GrantedAccessResponse
import com.angoga.kfd_workshop_server.model.response.web_authn.WebAuthLoginResponse

interface WebAuthService {

    fun register(request: WebAuthRegistrationRequest) : MessageResponse

    fun login(request: WebAuthLoginRequest) : WebAuthLoginResponse

    fun grantAccess(request: WebAuthGrantAccessRequest) : MessageResponse

    fun checkAccess() : GrantedAccessResponse

}